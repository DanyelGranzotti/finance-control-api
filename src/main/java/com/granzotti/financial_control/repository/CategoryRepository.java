package com.granzotti.financial_control.repository;

import com.granzotti.financial_control.model.Category;
import com.granzotti.financial_control.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findCategoryByUser(User user, Pageable pageable);
}
