package com.worldline.taskmanager.controller;

import com.worldline.taskmanager.dto.request.TaskGroupRequestDTO;
import com.worldline.taskmanager.dto.request.TaskRequestDTO;
import com.worldline.taskmanager.dto.response.TaskGroupResponseDTO;
import com.worldline.taskmanager.service.TaskGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task-group")
@RequiredArgsConstructor
@Tag(name = "Task Group API", description = "Manage task groups and their associated tasks")
public class TaskGroupController {

    private final TaskGroupService taskGroupService;

    @Operation(summary = "Get all task groups with the associated tasks")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_TASK_GROUP_EDITOR')")
    public List<TaskGroupResponseDTO> all() {
        return taskGroupService.fetchAllTaskGroups();
    }

    @Operation(summary = "Create a new task group")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_TASK_GROUP_EDITOR')")
    public TaskGroupResponseDTO create(@RequestBody @Valid TaskGroupRequestDTO taskGroupRequestDTO) {
        return taskGroupService.createTaskGroup(taskGroupRequestDTO);
    }

    @Operation(summary = "Add a new task to a task group")
    @PostMapping("/{taskGroupId}/task")
    @PreAuthorize("hasRole('ROLE_TASK_GROUP_EDITOR')")
    public TaskGroupResponseDTO createNewTask(@PathVariable Long taskGroupId,
                                              @RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        return taskGroupService.createNewTask(taskGroupId, taskRequestDTO);
    }

    @Operation(summary = "Update an existing task in a task group")
    @PutMapping("/{taskGroupId}/task/{taskId}")
    @PreAuthorize("hasRole('ROLE_TASK_GROUP_EDITOR')")
    public TaskGroupResponseDTO updateTask(@PathVariable Long taskGroupId,
                                           @PathVariable Long taskId,
                                           @RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        return taskGroupService.updateTask(taskGroupId, taskId, taskRequestDTO);
    }

    @Operation(summary = "Delete a task from a task group")
    @DeleteMapping("/{taskGroupId}/task/{taskId}")
    @PreAuthorize("hasRole('ROLE_TASK_GROUP_EDITOR')")
    public TaskGroupResponseDTO deleteTask(@PathVariable Long taskGroupId, @PathVariable Long taskId) {
        return taskGroupService.deleteTask(taskGroupId, taskId);
    }

    @Operation(summary = "Delete a task group and all its associated tasks")
    @DeleteMapping("/{taskGroupId}")
    @PreAuthorize("hasRole('ROLE_TASK_GROUP_EDITOR')")
    public void deleteTaskGroup(@PathVariable Long taskGroupId) {
        taskGroupService.deleteTaskGroup(taskGroupId);
    }
}
