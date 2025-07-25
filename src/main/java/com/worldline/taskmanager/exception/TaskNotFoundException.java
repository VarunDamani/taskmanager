package com.worldline.taskmanager.exception;

import jakarta.persistence.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {

    public TaskNotFoundException(Long id) {
        super(String.format("Task not found by id %d", id));
    }
}
