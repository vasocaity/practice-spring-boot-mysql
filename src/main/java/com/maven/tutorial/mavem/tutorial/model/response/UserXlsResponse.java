package com.maven.tutorial.mavem.tutorial.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class UserXlsResponse {
    String firstName;
    String lastName;
    String email;
    String facebook;
    String instagram;

}
