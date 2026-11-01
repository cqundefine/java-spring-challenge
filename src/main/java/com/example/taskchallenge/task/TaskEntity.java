package com.example.taskchallenge.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "calculation_tasks")
public class TaskEntity {

    @Id
    private UUID taskId;

    @Column(name = "input_value", nullable = false)
    private double value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CalculationFunction function;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    private Double result;
    private String error;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant completedAt;

    protected TaskEntity() {
    }

    private TaskEntity(CalculationTask task) {
        this.taskId = task.taskId();
        this.value = task.value();
        this.function = task.function();
        this.status = task.status();
        this.result = task.result();
        this.error = task.error();
        this.createdAt = task.createdAt();
        this.completedAt = task.completedAt();
    }

    public static TaskEntity from(CalculationTask task) {
        return new TaskEntity(task);
    }

    public CalculationTask toCalculationTask() {
        return new CalculationTask(taskId, value, function, status, result, error, createdAt, completedAt);
    }
}
