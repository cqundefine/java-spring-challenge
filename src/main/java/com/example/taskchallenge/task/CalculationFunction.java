package com.example.taskchallenge.task;

import java.util.Locale;

public enum CalculationFunction {
    SIN,
    COS,
    TAN;

    public static CalculationFunction from(String value) {
        try {
            return valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("function must be one of: sin, cos, tan");
        }
    }
}
