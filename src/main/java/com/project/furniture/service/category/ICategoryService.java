package com.project.furniture.service.category;

import com.project.furniture.model.category.Category;
import com.project.furniture.service.IService;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;

public interface ICategoryService extends IService<Category,Long> {

}
