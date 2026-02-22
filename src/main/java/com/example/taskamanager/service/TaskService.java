package com.example.taskamanager.service;

import com.example.taskamanager.dto.TaskRequestDTO;
import com.example.taskamanager.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO requestDTO);
    Page<TaskResponseDTO> getAllTasks(Pageable pageable);
    TaskResponseDTO getTaskById(Long id);
    TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO);
    void deleteTask(Long id);
    TaskResponseDTO markTaskCompleted(Long id);
    Page<TaskResponseDTO> getTasksByStatus(Boolean status,Pageable pageable);
}
