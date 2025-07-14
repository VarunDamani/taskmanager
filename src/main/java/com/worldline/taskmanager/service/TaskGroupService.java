package com.worldline.taskmanager.service;

import com.worldline.taskmanager.dto.request.TaskGroupRequestDTO;
import com.worldline.taskmanager.dto.request.TaskRequestDTO;
import com.worldline.taskmanager.dto.response.TaskGroupResponseDTO;
import com.worldline.taskmanager.entity.TaskEntity;
import com.worldline.taskmanager.entity.TaskGroupEntity;
import com.worldline.taskmanager.exception.TaskGroupAssociationException;
import com.worldline.taskmanager.exception.TaskGroupNotFoundException;
import com.worldline.taskmanager.exception.TaskNotFoundException;
import com.worldline.taskmanager.mapper.TaskGroupMapper;
import com.worldline.taskmanager.mapper.TaskMapper;
import com.worldline.taskmanager.repository.TaskGroupRepository;
import com.worldline.taskmanager.repository.TaskRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskGroupService {

    private final TaskGroupRepository taskGroupRepository;
    private final TaskRepository taskRepository;
    private final TaskGroupMapper taskGroupMapper;
    private final TaskMapper taskMapper;

    /**
     * Retrieves all task groups.
     *
     * @return a list of TaskGroupResponseDTO representing all task groups.
     */
    public List<TaskGroupResponseDTO> fetchAllTaskGroups() {
        return taskGroupRepository.findAll().stream().map(taskGroupMapper::toResponseDto).toList();
    }

    /**
     * Creates a new task group.
     *
     * @param taskGroupRequestDTO the request DTO containing task group information.
     * @return the created TaskGroupResponseDTO.
     */
    public TaskGroupResponseDTO createTaskGroup(TaskGroupRequestDTO taskGroupRequestDTO) {
        TaskGroupEntity entity = taskGroupMapper.toEntity(taskGroupRequestDTO);
        return taskGroupMapper.toResponseDto(taskGroupRepository.save(entity));
    }

    /**
     * Creates a new task within an existing task group.
     *
     * @param taskGroupId    the ID of the task group.
     * @param taskRequestDTO the request DTO containing task information.
     * @return the updated TaskGroupResponseDTO after adding the new task.
     */
    @Transactional
    public TaskGroupResponseDTO createNewTask(Long taskGroupId, TaskRequestDTO taskRequestDTO) {
        TaskGroupEntity taskGroupEntity = taskGroupRepository.findById(taskGroupId).orElseThrow(() -> new TaskGroupNotFoundException(taskGroupId));

        TaskEntity taskEntity = taskMapper.toEntity(taskRequestDTO);
        taskEntity.setTaskGroup(taskGroupEntity);
        taskRepository.save(taskEntity);

        taskGroupEntity.getTasks().add(taskEntity);

        log.info("A new task with id: {} created and added successfully to task group id: {}", taskEntity.getId(), taskGroupId);
        return taskGroupMapper.toResponseDto(taskGroupRepository.save(taskGroupEntity));
    }

    /**
     * Updates an existing task within a task group.
     *
     * @param taskGroupId    the ID of the task group.
     * @param taskId         the ID of the task.
     * @param taskRequestDTO the request DTO containing updated task information.
     * @return the updated TaskGroupResponseDTO after task update.
     */
    @Transactional
    public TaskGroupResponseDTO updateTask(Long taskGroupId, Long taskId, @Valid TaskRequestDTO taskRequestDTO) {
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        validateTaskGroupAssociation(taskEntity, taskGroupId);

        taskEntity.setName(taskRequestDTO.getName());
        taskEntity.setDescription(taskRequestDTO.getDescription());
        taskRepository.save(taskEntity);

        log.info("Task id: {} associated with task group id: {} updated successfully", taskId, taskGroupId);
        return taskGroupMapper.toResponseDto(taskEntity.getTaskGroup());
    }

    /**
     * Deletes a task from a task group.
     *
     * @param taskGroupId the ID of the task group.
     * @param taskId      the ID of the task.
     * @return the updated TaskGroupResponseDTO after task deletion.
     */
    @Transactional
    public TaskGroupResponseDTO deleteTask(Long taskGroupId, Long taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        validateTaskGroupAssociation(taskEntity, taskGroupId);

        TaskGroupEntity taskGroup = taskEntity.getTaskGroup();
        taskGroup.getTasks().remove(taskEntity);

        log.info("Task id: {} deleted successfully from task group id: {}", taskId, taskGroupId);
        return taskGroupMapper.toResponseDto(taskGroupRepository.save(taskGroup));
    }

    /**
     * Deletes an entire task group.
     *
     * @param taskGroupId the ID of the task group to delete.
     */
    public void deleteTaskGroup(Long taskGroupId) {
        taskGroupRepository.deleteById(taskGroupId);
        log.info("Task group id: {} deleted successfully", taskGroupId);
    }

    /**
     * Validates if a task entity belongs to the expected task group.
     *
     * @param taskEntity          the task entity.
     * @param expectedTaskGroupId the expected task group ID.
     */
    private void validateTaskGroupAssociation(TaskEntity taskEntity, Long expectedTaskGroupId) {
        if (!taskEntity.getTaskGroup().getId().equals(expectedTaskGroupId)) {
            log.error("Task id: {} is not associated with task group id: {}", taskEntity.getId(), expectedTaskGroupId);
            throw new TaskGroupAssociationException(expectedTaskGroupId, taskEntity.getId());
        }
    }
}
