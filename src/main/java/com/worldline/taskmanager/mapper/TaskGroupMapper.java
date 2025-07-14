package com.worldline.taskmanager.mapper;

import com.worldline.taskmanager.dto.request.TaskGroupRequestDTO;
import com.worldline.taskmanager.dto.response.TaskGroupResponseDTO;
import com.worldline.taskmanager.entity.TaskGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface TaskGroupMapper {
    @Mapping(target = "taskGroupId", source = "entity.id")
    TaskGroupResponseDTO toResponseDto(TaskGroupEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TaskGroupEntity toEntity(TaskGroupRequestDTO dto);
}
