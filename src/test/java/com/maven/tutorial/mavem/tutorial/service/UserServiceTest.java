package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.exception.NoDataFoundException;
import com.maven.tutorial.mavem.tutorial.exception.UserException;
import com.maven.tutorial.mavem.tutorial.model.entity.Address;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import com.maven.tutorial.mavem.tutorial.model.request.LoginRequest;
import com.maven.tutorial.mavem.tutorial.model.request.UserRequest;
import com.maven.tutorial.mavem.tutorial.model.response.UserResponse;
import com.maven.tutorial.mavem.tutorial.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository mockRepo;
    @Mock
    private PasswordEncoder mockPassword;
    @Mock
    private AddressService addressService;
    @Mock
    private SocialService socialService;
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private  UserService service;

    @Test
    void  should_call_find_by_id() throws Exception {
        User mockReturn = new User();
        mockReturn.setId(1);
        mockReturn.setEmail("user@mail.com");
        mockReturn.setFirstName("first");
        mockReturn.setLastName("last");
        mockReturn.setPassword("123kdfnlkdfnbfndflknb");
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(new Address());
        mockReturn.setAddresses(addresses);
        when(mockRepo.findById(1)).thenReturn(Optional.of(mockReturn));
       UserResponse result;
        result = service.findUserById(1);
        assertEquals("user@mail.com", result.getEmail());
    }

    @Test
    void findUserById_shouldThrowNoData() {
        when(mockRepo.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class,  () -> {
            service.findUserById(1);
        });
        assertEquals("No data found", exception.getMessage());
    }

    @Test
    void create() {
        UserRequest mockUser = new UserRequest();
        mockUser.setEmail("user@mail.com");
        mockUser.setFirstName("first");
        mockUser.setLastName("last");
        mockUser.setPassword("123kdfnlkdfnbfndflknb");
        service.create(mockUser);
        verify(mockRepo, times(1)).save(any());
    }

    @Test
    void login_login_fail_not_found_user() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@mail.com");
        when(mockRepo.findByEmail("user@mail.com")).thenReturn(Optional.empty());
        UserException exception = assertThrows(UserException.class, () -> {
            service.login(loginRequest);
        });
        assertEquals("user.login.fail", exception.getMessage());
    }
    
    @Test
    void  login_password_not_match() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@mail.com");
        loginRequest.setPassword("1234");

        User mockReturn = new User();
        mockReturn.setId(1);
        mockReturn.setEmail("user@mail.com");
        mockReturn.setFirstName("first");
        mockReturn.setLastName("last");
        mockReturn.setPassword("123kdfnlkdfnbfndflknb");

        when(mockRepo.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mockReturn));
        UserException exception = assertThrows(UserException.class, () -> {
            service.login(loginRequest);
        });
        assertEquals("user.login.fail", exception.getMessage());
    }
}
