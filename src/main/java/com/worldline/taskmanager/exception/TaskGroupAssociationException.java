package com.worldline.taskmanager.exception;

public class TaskGroupAssociationException extends RuntimeException {

    public TaskGroupAssociationException(Long taskGroupId, Long taskId) {
        super(String.format("Task id: %d is not associated to task group id: %d", taskId, taskGroupId));
    }
}
