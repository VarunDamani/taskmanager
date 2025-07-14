package com.worldline.taskmanager.mapper;

import com.worldline.taskmanager.dto.request.TaskRequestDTO;
import com.worldline.taskmanager.dto.response.TaskResponseDTO;
import com.worldline.taskmanager.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "taskId", source = "entity.id")
    TaskResponseDTO toResponseDto(TaskEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "taskGroup", ignore = true)
    TaskEntity toEntity(TaskRequestDTO dto);
}
