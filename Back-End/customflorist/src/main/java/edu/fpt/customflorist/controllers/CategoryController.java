package edu.fpt.customflorist.controllers;

import edu.fpt.customflorist.dtos.Category.CategoryDTO;
import edu.fpt.customflorist.models.Category;
import edu.fpt.customflorist.responses.Category.CategoryResponse;
import edu.fpt.customflorist.responses.ResponseObject;
import edu.fpt.customflorist.services.Category.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/api/v1/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseObject> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            Category category = categoryService.createCategory(categoryDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Category created successfully")
                            .status(HttpStatus.OK)
                            .data(CategoryResponse.fromEntity(category))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .data(null)
                            .build()
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            Category category = categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Category updated successfully")
                            .status(HttpStatus.OK)
                            .data(CategoryResponse.fromEntity(category))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .data(null)
                            .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Category retrieved successfully")
                            .status(HttpStatus.OK)
                            .data(CategoryResponse.fromEntity(category))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .data(null)
                            .build()
            );
        }
    }

    @GetMapping
    public ResponseEntity<ResponseObject> findAllCategories(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<CategoryResponse> categories = categoryService.findAll(keyword, pageable)
                    .map(CategoryResponse::fromEntity);

            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Categories retrieved successfully")
                            .status(HttpStatus.OK)
                            .data(categories)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .data(null)
                            .build()
            );
        }
    }

}
