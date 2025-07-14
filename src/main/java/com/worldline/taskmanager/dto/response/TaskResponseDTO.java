package com.worldline.taskmanager.dto.response;

public record TaskResponseDTO(
        Long taskId,
        String name,
        String description) {
}
