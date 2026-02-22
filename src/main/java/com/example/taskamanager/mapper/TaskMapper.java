package com.example.taskamanager.mapper;

import com.example.taskamanager.dto.TaskRequestDTO;
import com.example.taskamanager.dto.TaskResponseDTO;
import com.example.taskamanager.entity.Task;

public class TaskMapper {
    public static Task toEntity(TaskRequestDTO dto){
        return Task.builder().
                title(dto.getTitle()).
                description(dto.getDescription()).
                dueDate(dto.getDueDate()).
                build();
    }

    public static TaskResponseDTO toResponseDTO(Task task){
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setStatus(task.getStatus());
        dto.setDescription(task.getDescription());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setDueDate(task.getDueDate());
        return dto;
    }
}
