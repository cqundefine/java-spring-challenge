package com.example.taskchallenge.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CalculationRequest(
        @NotNull Double value,
        @NotBlank String function
) {
}
