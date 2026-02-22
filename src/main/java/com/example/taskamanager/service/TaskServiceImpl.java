package com.example.taskamanager.service;

import com.example.taskamanager.dto.TaskRequestDTO;
import com.example.taskamanager.dto.TaskResponseDTO;
import com.example.taskamanager.entity.Task;
import com.example.taskamanager.mapper.TaskMapper;
import com.example.taskamanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.taskamanager.exception.ResourceNotFoundException;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;
    @Override
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO){
        Task task = TaskMapper.toEntity(requestDTO);
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toResponseDTO(savedTask);
    }

    @Override
    public Page<TaskResponseDTO> getAllTasks(Pageable pageable){
        return taskRepository.findAll(pageable).map(TaskMapper::toResponseDTO);
    }

    @Override
    public TaskResponseDTO getTaskById(Long id){
        Task task = taskRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Task not found with id: "+ id));
        return TaskMapper.toResponseDTO(task);
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO){
        Task task = taskRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Task not found with id: "+ id));
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setDueDate(requestDTO.getDueDate());
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toResponseDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id){
        Task task = taskRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Task not found with id: "+ id));
        taskRepository.delete(task);
    }

    @Override
    public TaskResponseDTO markTaskCompleted(Long id){
        Task task = taskRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Task not found with id: "+ id));
        task.setStatus(Boolean.TRUE);
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toResponseDTO(updatedTask);
    }

    @Override
    public Page<TaskResponseDTO> getTasksByStatus(Boolean status, Pageable pageable){
        return taskRepository.findByStatus(status,pageable).map(TaskMapper::toResponseDTO);
    }

}
