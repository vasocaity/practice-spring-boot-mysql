package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.exception.BaseException;
import com.maven.tutorial.mavem.tutorial.model.entity.Address;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
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
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestUserService {
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
}
