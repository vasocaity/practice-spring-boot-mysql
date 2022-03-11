package com.maven.tutorial.mavem.tutorial.api;

import com.maven.tutorial.mavem.tutorial.exception.BaseException;
import com.maven.tutorial.mavem.tutorial.model.request.LoginRequest;
import com.maven.tutorial.mavem.tutorial.model.request.UserRequest;
import com.maven.tutorial.mavem.tutorial.model.response.UserResponse;
import com.maven.tutorial.mavem.tutorial.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RequestMapping("api/v1/users")
@RestController
@AllArgsConstructor
public class UserApi {
    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Integer id) throws Exception {
        UserResponse user = service.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String token = service.refreshToken();
        return ResponseEntity.ok(token);
    }

    @PostMapping("register")
    public ResponseEntity<String> create(@RequestBody UserRequest userRequest) {
        service.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("login")
    public ResponseEntity<String> create(@RequestBody LoginRequest request) throws BaseException {
        String response = service.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("xls")
    public ResponseEntity<ByteArrayResource> getFile() throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ProductTemplate.xlsx");
        try {
            return new ResponseEntity<>(new ByteArrayResource(service.getXsl()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
