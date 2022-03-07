package com.maven.tutorial.mavem.tutorial.api;

import com.maven.tutorial.mavem.tutorial.exception.BaseException;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import com.maven.tutorial.mavem.tutorial.model.request.LoginRequest;
import com.maven.tutorial.mavem.tutorial.model.request.UserRequest;
import com.maven.tutorial.mavem.tutorial.model.response.UserResponse;
import com.maven.tutorial.mavem.tutorial.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/v1/users")
@RestController
@AllArgsConstructor
public class UserApi {
    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Integer id) throws BaseException {
        UserResponse user = service.findUserById(id);
        return  ResponseEntity.ok(user);
    }

    @PostMapping("register")
    public ResponseEntity<String> create(@RequestBody UserRequest userRequest) {
        service.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("login")
    public ResponseEntity<String> create(@RequestBody LoginRequest request) throws BaseException {
       String response =  service.login(request);
        return ResponseEntity.ok(response);
    }
}
