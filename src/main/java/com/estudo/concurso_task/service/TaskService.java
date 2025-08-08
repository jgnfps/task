package com.estudo.concurso_task.service;

import com.estudo.concurso_task.dto.TaskRequestDTO;
import com.estudo.concurso_task.dto.TaskResponseDTO;
import com.estudo.concurso_task.entity.Task;
import com.estudo.concurso_task.entity.User;
import com.estudo.concurso_task.repository.TaskRepository;
import com.estudo.concurso_task.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        User user = userRepository.findById(taskRequestDTO.userId())
                .orElseThrow(()-> new EntityNotFoundException("User not found"));

        Task task = new Task();

        task.setTitle(taskRequestDTO.title());
        task.setDescription(taskRequestDTO.description());
        task.setUser(user);
        task = taskRepository.save(task);

        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), user.getId(), user.getUsername());
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com ID: " + id));

        taskRepository.delete(task);
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));

        if (dto.title() == null || dto.title().isBlank()) {
            throw new IllegalArgumentException("O título é obrigatório");
        }

        if (dto.description() == null || dto.description().isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória");
        }

        task.setTitle(dto.title());
        task.setDescription(dto.description());

        if (dto.userId() != null) {
            User user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + dto.userId()));
            task.setUser(user);
        }

        Task updatedTask = taskRepository.save(task);

        return new TaskResponseDTO(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getUser().getId(),
                updatedTask.getUser().getUsername()
        );
    }


    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getUser().getId(),
                        task.getUser().getUsername()
                ))
                .toList();
    }


}
