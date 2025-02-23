package edu.fpt.customflorist.services.Category;

import edu.fpt.customflorist.dtos.Category.CategoryDTO;
import edu.fpt.customflorist.models.Category;
import edu.fpt.customflorist.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) throws Exception {
        if (categoryRepository.findByNameContaining(categoryDTO.getName(), Pageable.unpaged()).hasContent()) {
            throw new Exception("Category with this name already exists");
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setIsActive(categoryDTO.getIsActive());

        return categoryRepository.save(category);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) throws Exception {
        Category category = categoryRepository.findByNameContaining(categoryDTO.getName(), Pageable.unpaged())
                .stream().findFirst()
                .orElseThrow(() -> new Exception("Category not found"));

        category.setDescription(categoryDTO.getDescription());
        category.setIsActive(categoryDTO.getIsActive());

        categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) throws Exception {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new Exception("Category not found"));
    }

    @Override
    public Page<Category> findAll(String keyword, Pageable pageable) throws Exception {
        return categoryRepository.findByNameContaining(keyword, pageable);
    }
}
