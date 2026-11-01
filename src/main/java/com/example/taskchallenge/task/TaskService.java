package com.example.taskchallenge.task;

import com.example.taskchallenge.config.KafkaTopicConfig;
import java.time.Instant;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskStore taskStore;
    private final KafkaTemplate<String, CalculationTask> kafkaTemplate;

    public TaskService(TaskStore taskStore, KafkaTemplate<String, CalculationTask> kafkaTemplate) {
        this.taskStore = taskStore;
        this.kafkaTemplate = kafkaTemplate;
    }

    public UUID submit(CalculationRequest request) {
        CalculationFunction function = CalculationFunction.from(request.function());
        UUID taskId = UUID.randomUUID();
        CalculationTask task = new CalculationTask(
                taskId, request.value(), function, TaskStatus.QUEUED, null, null, Instant.now(), null
        );

        taskStore.save(task);
        kafkaTemplate.send(KafkaTopicConfig.TASKS_TOPIC, taskId.toString(), task);
        return taskId;
    }

    public CalculationTask get(UUID taskId) {
        return taskStore.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }
}
