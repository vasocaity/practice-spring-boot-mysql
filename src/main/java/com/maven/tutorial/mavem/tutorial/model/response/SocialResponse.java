package com.maven.tutorial.mavem.tutorial.model.response;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
@Builder(toBuilder = true)
public class SocialResponse {
    private String firstName;
    private String lastName;
    private Integer Id;
    private String facebook;
    private String twitter;
    private String instagram;
    private Date created;
}
