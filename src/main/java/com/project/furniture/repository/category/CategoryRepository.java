package com.project.furniture.repository.category;

import com.project.furniture.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
