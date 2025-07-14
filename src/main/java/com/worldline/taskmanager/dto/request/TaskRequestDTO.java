package com.worldline.taskmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
