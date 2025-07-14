package com.worldline.taskmanager.exception;

import jakarta.persistence.EntityNotFoundException;

public class TaskGroupNotFoundException extends EntityNotFoundException {

    public TaskGroupNotFoundException(Long id) {
        super(String.format("Task group not found by id %d", id));
    }
}
