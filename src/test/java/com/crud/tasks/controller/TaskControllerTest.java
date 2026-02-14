package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DbService dbService;
    @MockitoBean
    private TaskMapper taskMapper;

    TaskDto taskDto = new TaskDto(1L, "Test task", "Test content");
    TaskDto taskDto2 = new TaskDto(2L, "Test task 2", "Test content 2");
    List<TaskDto> tasksDto = List.of(taskDto, taskDto2);

    Task task = new Task(1L, "Test task", "Test content");
    Task task2 = new Task(2L, "Test task 2", "Test content 2");
    List<Task> tasks = List.of(task, task2);

    TaskDto updatedTaskDto = new TaskDto(1L, "Updated task", "Updated content");
    Task updatedTask = new Task(1L, "Updated task", "Updated content");


    @Test
    void shouldGetTasks() throws Exception {
        //Given
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);

        //when & then
        mockMvc.perform(
                get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test task")))
                .andExpect(jsonPath("$[0].content", is("Test content")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Test task 2")))
                .andExpect(jsonPath("$[1].content", is("Test content 2")));
    }

    @Test
    void shouldGetTask() throws Exception {
        //Given
        when(dbService.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //when & then
        mockMvc.perform(
                get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test task")))
                .andExpect(jsonPath("$.content", is("Test content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //when & then
        mockMvc.perform(delete("/v1/tasks/1")).andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        Gson gson = new Gson();
        String json = gson.toJson(updatedTaskDto);

        when(taskMapper.mapToTaskDto(dbService.saveTask(updatedTask))).thenReturn(updatedTaskDto);

        //When & Then
        mockMvc.perform(
                put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Updated task")))
                .andExpect(jsonPath("$.content", is("Updated content")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        Gson gson = new Gson();
        String json = gson.toJson(taskDto);

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(
                post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }
}