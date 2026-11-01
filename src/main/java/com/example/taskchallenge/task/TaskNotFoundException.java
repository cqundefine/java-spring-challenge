package com.example.taskchallenge.task;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(UUID taskId) {
        super("Task %s was not found".formatted(taskId));
    }
}
