package com.maven.tutorial.mavem.tutorial.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDeleteResponse {
    private boolean success;
}
