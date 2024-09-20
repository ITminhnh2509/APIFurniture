package com.project.furniture.service.category;

import com.project.furniture.model.category.Category;
import com.project.furniture.repository.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository repository;


    @Override
    public Page<Category> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getById(Long aLong) {
        return repository.findById(aLong).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category existingCategory = repository.findById(id).orElse(null);
        if (existingCategory != null) {

            return repository.save(existingCategory);
        }
        return null;
    }


    @Override
    public void remove(Long id) {
        Category existingCategory = repository.findById(id).orElse(null);
        if(existingCategory != null) {
            existingCategory.setActive(false);
            repository.save(existingCategory);
        } else {
            throw new EntityNotFoundException("Category not found with id " + id);
        }

    }
}
