package com.usedcar.system.controller;

import com.usedcar.system.dto.CategoryRequest;
import com.usedcar.system.entity.Category;
import com.usedcar.system.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest request) {
        Category category = Category.builder()
                .name(request.name())
                .parentId(request.parentId())
                .sortOrder(request.sortOrder() == null ? 0 : request.sortOrder())
                .build();
        return ResponseEntity.ok(categoryRepository.save(category));
    }
}
