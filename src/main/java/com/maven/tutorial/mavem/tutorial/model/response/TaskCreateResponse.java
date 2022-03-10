package com.maven.tutorial.mavem.tutorial.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskCreateResponse {
    private TaskResponse data;
}
