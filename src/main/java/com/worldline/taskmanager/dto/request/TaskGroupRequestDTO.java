package com.worldline.taskmanager.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class TaskGroupRequestDTO {
    @NotBlank
    String name;

    @Valid
    List<TaskRequestDTO> tasks;
}
