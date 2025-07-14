package com.worldline.taskmanager.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Data
@AllArgsConstructor
public class ApiError {
    private int code;

    private String exception;

    private String message;

    private String translation;

    private Map<String, Set<String>> errors;

    public static ApiError notFound(Exception exception) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getClass().getSimpleName(), exception.getMessage(),
                null, null);
    }

    public static ApiError invalidRequest(Exception exception) {
        return new ApiError(HttpStatus.BAD_REQUEST.value(), exception.getClass().getSimpleName(), exception.getMessage(),
                null, null);
    }

    public static ApiError requestDataNotValid(MethodArgumentNotValidException exception) {
        Map<String, Set<String>> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");

            Set<String> fieldErrors = errors.getOrDefault(field, new HashSet<>());
            fieldErrors.add(message);
            errors.put(field, fieldErrors);
        });

        return new ApiError(HttpStatus.BAD_REQUEST.value(), exception.getClass().getSimpleName(), "Validation failed",
                null, errors);
    }
}
