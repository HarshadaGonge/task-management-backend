package com.example.taskamanager.controller;

import com.example.taskamanager.dto.TaskResponseDTO;
import com.example.taskamanager.exception.ResourceNotFoundException;
import com.example.taskamanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnTaskById() throws Exception{
        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitle("Test Task");
        responseDTO.setStatus(Boolean.FALSE);

        when(taskService.getTaskById(1L)).thenReturn(responseDTO);
        mockMvc.perform(get("/tasks/1")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception{
        when(taskService.getTaskById(1L)).
                thenThrow(new ResourceNotFoundException("Task not found"));
        mockMvc.perform(get("/tasks/1")).andExpect(
                status().isNotFound()
        );

    }
}
