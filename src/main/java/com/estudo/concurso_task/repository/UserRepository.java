package com.estudo.concurso_task.repository;

import com.estudo.concurso_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
