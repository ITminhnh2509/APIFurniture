package com.project.furniture.service.category;

import com.project.furniture.model.category.Category;
import com.project.furniture.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
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
        return repository.getById(aLong);
    }

    @Override
    public Category save(Category dto) {
        return repository.save(dto);
    }

    @Override
    public Category update(Long aLong, Category dto) {
        Category getCategory = getById(aLong);
        if(getCategory!=null){
            getCategory.setName(dto.getName());
        }
        return repository.save(getCategory);
    }

    @Override
    public void remove(Long aLong) {
        Category getCategory = getById(aLong);
        if(getCategory!=null){
            getCategory.setActive(false);
        }
        repository.delete(getCategory);
    }

}
