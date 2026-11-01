package com.example.taskchallenge.task;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TaskStore.class)
class TaskStoreJpaTest {

    @Autowired
    private TaskStore taskStore;

    @Test
    void persistsTaskAndMakesItAvailableById() {
        UUID taskId = UUID.randomUUID();
        CalculationTask task = new CalculationTask(
                taskId, 2.0, CalculationFunction.COS, TaskStatus.QUEUED, null, null, Instant.now(), null
        );

        taskStore.save(task);

        assertThat(taskStore.findById(taskId)).contains(task);
    }
}
