package com.estudo.concurso_task.controller;

import com.estudo.concurso_task.dto.CategoryRequestDTO;
import com.estudo.concurso_task.dto.CategoryResponseDTO;
import com.estudo.concurso_task.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias de tarefas")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Criar nova categoria",
            description = "Cadastra uma nova categoria de tarefas",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
            }
    )
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody @Valid CategoryRequestDTO dto) {
        return ResponseEntity.status(201).body(categoryService.create(dto));
    }

    @Operation(
            summary = "Listar todas as categorias",
            description = "Retorna uma lista com todas as categorias cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna os dados de uma categoria específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos enviados"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @Operation(
            summary = "Excluir categoria",
            description = "Remove uma categoria existente",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

