package com.granzotti.financial_control.service;

import com.granzotti.financial_control.model.Category;
import com.granzotti.financial_control.model.User;
import com.granzotti.financial_control.repository.CategoryRepository;
import com.granzotti.financial_control.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SecurityUtils securityUtils;

    public CategoryService(CategoryRepository categoryRepository, SecurityUtils securityUtils) {
        this.categoryRepository = categoryRepository;
        this.securityUtils = securityUtils;
    }

    public Category createCategory(Category category) {
        User user = securityUtils.getAuthenticatedUser();
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        User user = securityUtils.getAuthenticatedUser();
        validateOwnership(id, user);

        Category currentCategory = findById(id);
        currentCategory.setName(category.getName());
        return categoryRepository.save(currentCategory);
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        User user = securityUtils.getAuthenticatedUser();
        return categoryRepository.findCategoryByUser(user, pageable);
    }

    public void deleteCategory(Long id) {
        User user = securityUtils.getAuthenticatedUser();
        validateOwnership(id, user);

        categoryRepository.deleteById(id);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void validateOwnership(Long categoryId, User user) {
        Category category = findById(categoryId);
        if (!category.getUser().equals(user)) {
            throw new RuntimeException("You can't modify a category that doesn't belong to you");
        }
    }
}
