package com.example.taskchallenge.task;

import com.example.taskchallenge.config.KafkaTopicConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CalculationConsumer {

    private final TaskStore taskStore;

    public CalculationConsumer(TaskStore taskStore) {
        this.taskStore = taskStore;
    }

    @KafkaListener(topics = KafkaTopicConfig.TASKS_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void calculate(CalculationTask task) {
        taskStore.save(task.withStatus(TaskStatus.PROCESSING));

        try {
            double result = switch (task.function()) {
                case SIN -> Math.sin(task.value());
                case COS -> Math.cos(task.value());
                case TAN -> Math.tan(task.value());
            };
            taskStore.save(task.completed(result));
        } catch (RuntimeException exception) {
            taskStore.save(task.failed(exception.getMessage()));
        }
    }
}
