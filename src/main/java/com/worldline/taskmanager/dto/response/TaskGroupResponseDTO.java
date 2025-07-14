package com.worldline.taskmanager.dto.response;

import java.util.List;

public record TaskGroupResponseDTO(
        Long taskGroupId,
        String name,
        List<TaskResponseDTO> tasks) {
}
