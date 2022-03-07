package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.exception.BaseException;
import com.maven.tutorial.mavem.tutorial.exception.UserException;
import com.maven.tutorial.mavem.tutorial.model.entity.Address;
import com.maven.tutorial.mavem.tutorial.model.entity.Social;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import com.maven.tutorial.mavem.tutorial.model.request.LoginRequest;
import com.maven.tutorial.mavem.tutorial.model.request.UserRequest;
import com.maven.tutorial.mavem.tutorial.model.response.AddressResponse;
import com.maven.tutorial.mavem.tutorial.model.response.UserResponse;
import com.maven.tutorial.mavem.tutorial.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final SocialService socialService;
    private final AddressService addressService;
    private  final TokenService tokenService;

    public UserResponse findUserById(Integer id) throws BaseException {

        Optional<User> user =  repository.findById(id);
        if (user.isEmpty()) {
            throw UserException.UserExceptionNotFound();
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

}
