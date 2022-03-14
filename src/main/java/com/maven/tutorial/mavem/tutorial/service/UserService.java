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
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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
        // Sheet User
        Sheet sheet = workbook.createSheet("User");

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        //  Header
        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:E1"));
        Row header = sheet.createRow(0);
        Cell headerCell = header.createCell(0);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("USER");

        // Row 2
        Row row = sheet.createRow(1);
        List<String> rows = Arrays.asList("email", "firstName", "lastName", "facebook", "instagram");
        for (int i = 0; i < 5; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rows.get(i));
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
        Integer rowIdx = 2;
        for (UserXlsResponse response : userXlsResponses
        ) {
            Row rowContent = sheet.createRow(rowIdx++);
            rowContent.createCell(0).setCellValue(response.getEmail());
            rowContent.createCell(1).setCellValue(response.getFirstName());
            rowContent.createCell(2).setCellValue(response.getLastName());
            rowContent.createCell(3).setCellValue(response.getFacebook());
            rowContent.createCell(4).setCellValue(response.getInstagram());
        }

        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.DOCUMENT);
        link.setAddress("'Address'!A1");

        Row hyperlinkRow = sheet.createRow(7);
        Cell hyperlinkCell = hyperlinkRow.createCell(0);
        CellStyle hlinkstyle = workbook.createCellStyle();
        XSSFFont hlinkFont = ((XSSFWorkbook) workbook).createFont();
        hlinkFont.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        hlinkFont.setBold(true);
        hlinkstyle.setFont(hlinkFont);

        hyperlinkCell.setCellStyle(hlinkstyle);
        hyperlinkCell.setHyperlink(link);
        hyperlinkCell.setCellValue("Address");  // <-- important

        // Sheet Address
        Sheet sheetAddress = workbook.createSheet("Address");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return out.toByteArray();
    }

    public byte[] getWord() throws Exception {
        XWPFDocument document = new XWPFDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        out.close();
        document.close();
        return out.toByteArray();
    }
}
