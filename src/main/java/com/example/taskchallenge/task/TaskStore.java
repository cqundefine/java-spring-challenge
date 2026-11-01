package com.example.taskchallenge.task;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TaskStore {

    private final TaskRepository taskRepository;

    public TaskStore(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public CalculationTask save(CalculationTask task) {
        taskRepository.save(TaskEntity.from(task));
        return task;
    }

    public Optional<CalculationTask> findById(UUID taskId) {
        return taskRepository.findById(taskId).map(TaskEntity::toCalculationTask);
    }
}
