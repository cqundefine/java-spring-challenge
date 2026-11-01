package com.example.taskchallenge.task;

import java.time.Instant;
import java.util.UUID;

public record CalculationTask(
        UUID taskId,
        double value,
        CalculationFunction function,
        TaskStatus status,
        Double result,
        String error,
        Instant createdAt,
        Instant completedAt
) {
    public CalculationTask withStatus(TaskStatus newStatus) {
        return new CalculationTask(taskId, value, function, newStatus, result, error, createdAt, completedAt);
    }

    public CalculationTask completed(double calculationResult) {
        return new CalculationTask(
                taskId, value, function, TaskStatus.COMPLETED, calculationResult, null, createdAt, Instant.now()
        );
    }

    public CalculationTask failed(String message) {
        return new CalculationTask(taskId, value, function, TaskStatus.FAILED, null, message, createdAt, Instant.now());
    }
}
