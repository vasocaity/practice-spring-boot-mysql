package com.maven.tutorial.mavem.tutorial.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskResponse {
    private boolean completed;
    private String _id;
    private String description;
    private String owner;
    private String createdAt;
    private String updatedAt;
}
