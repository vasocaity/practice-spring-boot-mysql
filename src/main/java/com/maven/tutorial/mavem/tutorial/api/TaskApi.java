package com.maven.tutorial.mavem.tutorial.api;

import com.maven.tutorial.mavem.tutorial.model.response.TaskCreateResponse;
import com.maven.tutorial.mavem.tutorial.model.response.TaskDeleteResponse;
import com.maven.tutorial.mavem.tutorial.model.response.TaskListResponse;
import com.maven.tutorial.mavem.tutorial.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/tasks")
@RestController
@AllArgsConstructor
public class TaskApi {

    private final TaskService httpService;

    @GetMapping()
    public TaskListResponse getAllTask() {
        return httpService.getAllTasks();
    }

    @PostMapping()
    public TaskCreateResponse saveTask() {
        return httpService.saveTask();
    }

    @PutMapping("{id}/completed/{completed}")
    public TaskCreateResponse updateTask(@PathVariable("id") String id, @PathVariable("completed") boolean completed) {
        return httpService.updateTask(id, completed);
    }

    @DeleteMapping("{id}")
    public TaskDeleteResponse deleteTask(@PathVariable("id") String id) {
        return httpService.deleteTask(id);
    }
}
