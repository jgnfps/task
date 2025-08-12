package com.estudo.concurso_task.controller;

import com.estudo.concurso_task.dto.TaskRequestDTO;
import com.estudo.concurso_task.dto.TaskResponseDTO;
import com.estudo.concurso_task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Tag(name = "Tarefas", description = "Gerenciamento de tarefas")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Criar nova tarefa",
            description = "Cria uma nova tarefa a partir dos dados enviados no corpo da requisição",
            responses = {
                 @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
                 @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
         }
    )
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskRequestDTO dto) {
        TaskResponseDTO created = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Listar todas as tarefas",
            description = "Retorna uma lista de todas as tarefas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAll();
        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Buscar tarefa por ID",
            description = "Retorna os dados de uma tarefa específica pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getById(id);
        return ResponseEntity.ok(task);
    }


    @Operation(
            summary = "Atualizar tarefa",
            description = "Atualiza os dados de uma tarefa existente pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @Parameter(description = "ID da tarefa a ser atualizada")@PathVariable Long id,
            @RequestBody @Valid TaskRequestDTO dto
    ) {
        TaskResponseDTO updated = taskService.updateTask(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Excluir tarefa",
            description = "Remove uma tarefa existente pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@Parameter(description = "ID da tarefa a ser excluída")@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

}
