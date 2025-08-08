package com.estudo.concurso_task.service;

import com.estudo.concurso_task.dto.TaskRequestDTO;
import com.estudo.concurso_task.dto.TaskResponseDTO;
import com.estudo.concurso_task.entity.Task;
import com.estudo.concurso_task.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        if(taskRequestDTO.title() == null || taskRequestDTO.title().isBlank()){
            throw new IllegalArgumentException("O título é obrigatório");
        }

        if(taskRequestDTO.description() == null || taskRequestDTO.description().isBlank()){
            throw new IllegalArgumentException("A descrição é obrigatória");
        }

        Task task = new Task();

        task.setTitle(taskRequestDTO.title());
        task.setDescription(taskRequestDTO.description());
        task = taskRepository.save(task);

        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription());
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

        Task updatedTask = taskRepository.save(task);

        return new TaskResponseDTO(updatedTask.getId(), updatedTask.getTitle(), updatedTask.getDescription());
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com ID: " + id));

        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription());
    }

}
