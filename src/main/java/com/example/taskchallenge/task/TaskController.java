package com.example.taskchallenge.task;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskAcceptedResponse> submit(@Valid @RequestBody CalculationRequest request) {
        UUID taskId = taskService.submit(request);
        return ResponseEntity.accepted()
                .location(URI.create("/tasks/" + taskId))
                .body(new TaskAcceptedResponse(taskId));
    }

    @GetMapping("/{taskId}")
    public CalculationTask get(@PathVariable UUID taskId) {
        return taskService.get(taskId);
    }
}
