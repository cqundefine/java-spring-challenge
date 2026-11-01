package com.example.taskchallenge.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import com.example.taskchallenge.config.KafkaTopicConfig;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

class TaskServiceTest {

    @Test
    void storesAndPublishesNewTask() {
        TaskStore taskStore = org.mockito.Mockito.mock(TaskStore.class);
        @SuppressWarnings("unchecked")
        KafkaTemplate<String, CalculationTask> kafkaTemplate = org.mockito.Mockito.mock(KafkaTemplate.class);
        TaskService taskService = new TaskService(taskStore, kafkaTemplate);

        UUID taskId = taskService.submit(new CalculationRequest(2.0, "cos"));

        org.mockito.ArgumentCaptor<CalculationTask> taskCaptor =
                org.mockito.ArgumentCaptor.forClass(CalculationTask.class);
        verify(taskStore).save(taskCaptor.capture());
        CalculationTask task = taskCaptor.getValue();
        assertThat(task.taskId()).isEqualTo(taskId);
        assertThat(task.status()).isEqualTo(TaskStatus.QUEUED);
        assertThat(task.function()).isEqualTo(CalculationFunction.COS);
        verify(kafkaTemplate).send(eq(KafkaTopicConfig.TASKS_TOPIC), eq(taskId.toString()), any(CalculationTask.class));
    }
}
