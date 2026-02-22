package com.example.taskamanager.controller;

import com.example.taskamanager.dto.TaskRequestDTO;
import com.example.taskamanager.dto.TaskResponseDTO;
import com.example.taskamanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "View All Tasks", description = "View All Tasks")
    @GetMapping
    public Page<TaskResponseDTO> getAllTasks(Pageable pageable){
        return taskService.getAllTasks(pageable);
    }

    @Operation(summary = "View Task", description = "View The Task for Specific ID")
    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Update Task", description = "Update The Task Details")
    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO requestDTO){
        return taskService.updateTask(id,requestDTO);
    }

    @Operation(summary = "Delete Task", description = "Delete The Task with Specific ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    @Operation(summary = "Complete Task", description = "Mark The Task Completed for Specific ID")
    @PutMapping("/{id}/complete")
    public TaskResponseDTO markCompleted(@PathVariable Long id){
        return taskService.markTaskCompleted(id);
    }

    @Operation(summary = "View Tasks By Status", description = "View List of Task for Given Status")
    @GetMapping("/status/{status}")
    public Page<TaskResponseDTO> getTaskByStatus(@PathVariable Boolean status, Pageable pageable){
        return taskService.getTasksByStatus(status,pageable);
    }

    @Operation(summary = "Create Task", description = "Create The Task")
    @PostMapping("/tasks")
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO taskRequestDTO){
        return taskService.createTask(taskRequestDTO);
    }
}
