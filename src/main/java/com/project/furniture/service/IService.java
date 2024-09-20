package com.project.furniture.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IService<T, ID> {
        Page<T> getAll(Pageable pageable);

        List<T> getAll();

        T getById(ID id);

        T save(T dto);

        T update(ID id, T dto);

        void remove(ID id);
}
