package com.example.taskchallenge.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CalculationConsumerTest {

    private final TaskStore taskStore = org.mockito.Mockito.mock(TaskStore.class);
    private final CalculationConsumer consumer = new CalculationConsumer(taskStore);

    @Test
    void completesSineCalculation() {
        UUID taskId = UUID.randomUUID();
        CalculationTask task = new CalculationTask(
                taskId, Math.PI / 2, CalculationFunction.SIN, TaskStatus.QUEUED, null, null, Instant.now(), null
        );

        consumer.calculate(task);

        org.mockito.ArgumentCaptor<CalculationTask> taskCaptor =
                org.mockito.ArgumentCaptor.forClass(CalculationTask.class);
        verify(taskStore, times(2)).save(taskCaptor.capture());
        CalculationTask completedTask = taskCaptor.getAllValues().getLast();
        assertThat(completedTask.status()).isEqualTo(TaskStatus.COMPLETED);
        assertThat(completedTask.result()).isCloseTo(1.0, org.assertj.core.data.Offset.offset(0.000001));
        assertThat(completedTask.completedAt()).isNotNull();
    }
}
