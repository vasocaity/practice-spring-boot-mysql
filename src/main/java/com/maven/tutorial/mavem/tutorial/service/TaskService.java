package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.model.response.TaskCreateResponse;
import com.maven.tutorial.mavem.tutorial.model.response.TaskDeleteResponse;
import com.maven.tutorial.mavem.tutorial.model.response.TaskListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService {
    private final RestTemplate restTemplate;

    public TaskService() {
        this.restTemplate = new RestTemplate();
    }

    @Value(("${app.baseUrl}"))
    private String endpoint;

    @Value(("${app.todoToken}"))
    private String token;

    public TaskListResponse getAllTasks() {
        HttpEntity requestEntity = new HttpEntity<>(setupHttpHeadersWithAuth(new HttpHeaders()));
        ResponseEntity<TaskListResponse> responseEntity = restTemplate.exchange(endpoint + "/task", HttpMethod.GET, requestEntity, TaskListResponse.class);
        return responseEntity.getBody();
    }

    public TaskCreateResponse saveTask() {
        Map<String, String> body = new HashMap<>();
        body.put("description", "readinggg");
        HttpEntity requestEntity = new HttpEntity<>(body, setupHttpHeadersWithAuth(new HttpHeaders()));
        ResponseEntity<TaskCreateResponse> responseEntity = restTemplate.exchange(endpoint + "/task", HttpMethod.POST, requestEntity, TaskCreateResponse.class);
        return responseEntity.getBody();
    }

    public TaskCreateResponse updateTask(String id, boolean completed) {
        Map<String, Boolean> body = new HashMap<>();
        body.put("completed", completed);
        HttpEntity requestEntity = new HttpEntity<>(body, setupHttpHeadersWithAuth(new HttpHeaders()));
        ResponseEntity<TaskCreateResponse> responseEntity = restTemplate.exchange(endpoint + "/task/" + id, HttpMethod.PUT, requestEntity, TaskCreateResponse.class);
        return responseEntity.getBody();
    }

    private HttpHeaders setupHttpHeadersWithAuth(HttpHeaders httpHeaders) {
        httpHeaders.add("Authorization", "Bearer " + token);

        return httpHeaders;
    }

    public TaskDeleteResponse deleteTask(String id) {
        HttpEntity request = new HttpEntity(setupHttpHeadersWithAuth(new HttpHeaders()));
        ResponseEntity<TaskDeleteResponse> responseEntity = restTemplate.exchange(endpoint + "/task/" + id, HttpMethod.DELETE, request, TaskDeleteResponse.class);
        return responseEntity.getBody();
    }
}
