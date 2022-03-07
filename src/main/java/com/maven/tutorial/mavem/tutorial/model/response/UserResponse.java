package com.maven.tutorial.mavem.tutorial.model.response;

import com.maven.tutorial.mavem.tutorial.model.entity.Address;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    String firstName;
    String lastName;
    String email;
    String password;
    String facebook;
    String twitter;
    String instagram;
    List<AddressResponse> addresses;
}
