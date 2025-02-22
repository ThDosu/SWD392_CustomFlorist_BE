package edu.fpt.customflorist.services.Category;

import edu.fpt.customflorist.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    Category createCategory() throws Exception;
    void updateCategory() throws Exception;
    void deleteCategory() throws Exception;
    Category getCategoryById() throws Exception;
    Page<Category> findAll(String keyword, Pageable pageable) throws Exception;
}
