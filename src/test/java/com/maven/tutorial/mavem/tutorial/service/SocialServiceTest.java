package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.model.entity.Address;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import com.maven.tutorial.mavem.tutorial.model.response.UserResponse;
import com.maven.tutorial.mavem.tutorial.repository.SocialRepository;
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
public class SocialServiceTest {
    @Mock
    private SocialRepository mockRepo;

    @InjectMocks
    private  SocialService service;

}
