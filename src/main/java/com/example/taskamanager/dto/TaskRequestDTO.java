package com.example.taskamanager.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class TaskRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(max=255, message = "Title must not exceed 100 characters")
    private String title;

    private String description;

    @FutureOrPresent(message = "Due date cant be in the past")
    private LocalDate dueDate;
}
