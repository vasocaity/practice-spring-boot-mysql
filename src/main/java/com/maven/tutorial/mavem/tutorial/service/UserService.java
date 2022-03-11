package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.exception.BaseException;
import com.maven.tutorial.mavem.tutorial.exception.NoDataFoundException;
import com.maven.tutorial.mavem.tutorial.exception.UserException;
import com.maven.tutorial.mavem.tutorial.model.entity.Address;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import com.maven.tutorial.mavem.tutorial.model.request.LoginRequest;
import com.maven.tutorial.mavem.tutorial.model.request.UserRequest;
import com.maven.tutorial.mavem.tutorial.model.response.AddressResponse;
import com.maven.tutorial.mavem.tutorial.model.response.UserResponse;
import com.maven.tutorial.mavem.tutorial.model.response.UserXlsResponse;
import com.maven.tutorial.mavem.tutorial.repository.UserRepository;
import com.maven.tutorial.mavem.tutorial.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final SocialService socialService;
    private final AddressService addressService;
    private final TokenService tokenService;

    public UserResponse findUserById(Integer id) throws Exception {

        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NoDataFoundException();
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.get().getEmail());
        List<Address> addresses = user.get().getAddresses();
        List<AddressResponse> addressResponses = new ArrayList<AddressResponse>();
        addressResponses.add(new AddressResponse(addresses.get(0).getLine1()));
        userResponse.setAddresses(addressResponses);
        return userResponse;
    }

    @Transactional
    public void create(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        repository.save(user);
        socialService.create(userRequest, user);
        addressService.create(userRequest, user);

    }

    public String login(LoginRequest loginRequest) throws BaseException {
        Optional<User> user = repository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            throw UserException.LoginFail();
        }
        if (!matchPassword(loginRequest.getPassword(), user.get().getPassword())) {
            throw UserException.LoginFail();
        }
        String token = tokenService.tokenize(user.get());
        return token;
    }

    public boolean matchPassword(String rawPassword, String encodedPAssword) {
        return passwordEncoder.matches(rawPassword, encodedPAssword);
    }

    public String refreshToken() throws BaseException {
        Optional<Integer> opt = SecurityUtil.getCurrentUser();

        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }
        Integer userId = opt.get();

        Optional<User> optUser = repository.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.UserExceptionNotFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    public byte[] getXsl() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("User");

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);
        //  Row
        Row header = sheet.createRow(0);
        List<String> rows = Arrays.asList("email", "firstName", "lastName", "facebook", "instagram");
        for (int i = 0; i < 5; i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(rows.get(i));
            headerCell.setCellStyle(headerStyle);
            sheet.autoSizeColumn(i);
        }
        //  Cell
        List<User> users = (List<User>) repository.findAll();
        List<UserXlsResponse> userXlsResponses = users.stream().map(m ->
                        UserXlsResponse.builder()
                                .email(m.getEmail())
                                .facebook(m.getSocial().getFacebook())
                                .firstName(m.getFirstName())
                                .instagram(m.getSocial().getInstagram())
                                .lastName(m.getLastName())
                                .build()
                )
                .collect(Collectors.toList());
        Integer rowIdx = 1;
        for (UserXlsResponse response : userXlsResponses
        ) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(response.getEmail());
            row.createCell(1).setCellValue(response.getFirstName());
            row.createCell(2).setCellValue(response.getLastName());
            row.createCell(3).setCellValue(response.getFacebook());
            row.createCell(4).setCellValue(response.getInstagram());
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return out.toByteArray();
    }
}
