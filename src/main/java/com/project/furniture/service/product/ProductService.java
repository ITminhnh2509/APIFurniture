package com.project.furniture.service.product;

import com.project.furniture.model.product.Product;
import com.project.furniture.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository repository;


    @Override
    public Page<Product> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return List.of();
    }

    @Override
    public Product getById(Long aLong) {
        return repository.findById(aLong).orElse(null);
    }

    @Override
    public Product save(Product dto) {
        return null;
    }

    @Override
    public Product update(Long aLong, Product dto) {
        return null;
    }

    @Override
    public void remove(Long aLong) {
        Product getProduct = getById(aLong);
        if(getProduct!=null){
            getProduct.setActive(false);
        }
    }
}
