package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NonNullApi
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAll();
}
