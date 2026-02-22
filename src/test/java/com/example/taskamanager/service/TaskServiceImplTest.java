package com.example.taskamanager.service;

import com.example.taskamanager.dto.TaskRequestDTO;
import com.example.taskamanager.dto.TaskResponseDTO;
import com.example.taskamanager.entity.Task;
import com.example.taskamanager.exception.ResourceNotFoundException;
import com.example.taskamanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    void shouldCreateTaskSuccessfully(){
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setTitle("Test Task");
        taskRequestDTO.setDescription("Testing");
        taskRequestDTO.setDueDate(LocalDate.now());

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Test Task");
        savedTask.setDescription("Testing");
        savedTask.setStatus(Boolean.FALSE);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponseDTO responseDTO = taskService.createTask(taskRequestDTO);
        assertNotNull(responseDTO);
        assertEquals("Test Task",responseDTO.getTitle());
        assertEquals(Boolean.FALSE,responseDTO.getStatus());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldReturnTaskWhenExist(){
        Task task = new Task();
        task.setId(1L);
        task.setStatus(Boolean.FALSE);
        task.setTitle("Sample");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        TaskResponseDTO responseDTO = taskService.getTaskById(1L);
        assertEquals("Sample",responseDTO.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound(){
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->taskService.getTaskById(1L));
    }

    @Test
    void shouldReturnTaskByStatus(){
        Pageable pageable = PageRequest.of(0,10);
        Task task = new Task();
        task.setStatus(Boolean.TRUE);
        Page<Task> page = new PageImpl<>(List.of(task));
        when(taskRepository.findByStatus(Boolean.TRUE,pageable)).thenReturn(page);
        Page<TaskResponseDTO> result = taskService.getTasksByStatus(Boolean.TRUE,pageable);
        assertEquals(1,result.getTotalElements());
    }

}
