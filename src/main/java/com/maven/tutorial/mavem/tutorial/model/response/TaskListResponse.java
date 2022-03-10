package com.maven.tutorial.mavem.tutorial.model.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TaskListResponse {
    private Integer count;
    private ArrayList<TaskResponse> data;
}
