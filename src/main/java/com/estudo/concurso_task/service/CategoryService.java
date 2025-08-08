package com.estudo.concurso_task.service;

import com.estudo.concurso_task.dto.CategoryRequestDTO;
import com.estudo.concurso_task.dto.CategoryResponseDTO;
import com.estudo.concurso_task.entity.Category;
import com.estudo.concurso_task.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());

        Category saved = categoryRepository.save(category);
        return new CategoryResponseDTO(saved.getId(), saved.getName(), saved.getDescription());
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(cat -> new CategoryResponseDTO(cat.getId(), cat.getName(), cat.getDescription()))
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + id));

        return new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription());
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + id));

        category.setName(dto.name());
        category.setDescription(dto.description());

        Category updated = categoryRepository.save(category);
        return new CategoryResponseDTO(updated.getId(), updated.getName(), updated.getDescription());
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada com id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}

