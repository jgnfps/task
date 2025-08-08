package com.estudo.concurso_task.service;

import com.estudo.concurso_task.dto.TaskRequestDTO;
import com.estudo.concurso_task.dto.TaskResponseDTO;
import com.estudo.concurso_task.entity.Task;
import com.estudo.concurso_task.entity.User;
import com.estudo.concurso_task.repository.TaskRepository;
import com.estudo.concurso_task.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + dto.userId()));

        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setUser(user);

        task = taskRepository.save(task);

        return toResponseDTO(task);
    }

    public TaskResponseDTO getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));

        return toResponseDTO(task);
    }

    public List<TaskResponseDTO> getAll() {
        return taskRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));

        if (dto.title() != null && !dto.title().isBlank()) {
            task.setTitle(dto.title());
        }

        if (dto.description() != null && !dto.description().isBlank()) {
            task.setDescription(dto.description());
        }

        if (dto.userId() != null) {
            User user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + dto.userId()));
            task.setUser(user);
        }

        task = taskRepository.save(task);

        return toResponseDTO(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));

        taskRepository.delete(task);
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getUser().getId(),
                task.getUser().getUsername()
        );
    }
}
