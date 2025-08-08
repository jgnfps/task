package com.estudo.concurso_task.repository;

import com.estudo.concurso_task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
