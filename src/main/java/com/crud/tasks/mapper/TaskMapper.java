package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskMapper {

    public Task mapToTask(TaskDto taskDto) {
        return new Task(taskDto.getId(), taskDto.getTitle(), taskDto.getContent());
    }

    public TaskDto mapToTaskDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.getContent());
    }

    public List<TaskDto> mapToTaskDtoList(List<Task> tasks) {
        return tasks.stream().map(this::mapToTaskDto).toList();
    }
}
