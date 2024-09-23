package com.project.furniture.service.product;

import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.Product;
import com.project.furniture.model.product.ProuductImage;
import com.project.furniture.repository.product.ProductRepository;
import com.project.furniture.response.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository repository;


    @Override
    public Page<Product> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Product getById(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
    public Product save(Product dto) {
        return repository.save(dto);
    }

    @Override
    public Product update(Long aLong, Product dto) {
        Product product = getById(aLong);
        if (product != null){
            product.setName(dto.getName());
            product.setPrice(dto.getPrice());
            product.setDescription(dto.getDescription());
            product.setActive(dto.isActive());
            product.setCategory(dto.getCategory());
            product.setProductImages(dto.getProductImages());
        }
        return repository.save(product);
    }

    @Override
    public void remove(Long aLong) {
        Product getProduct = getById(aLong);
        if(getProduct!=null){
            getProduct.setActive(false);
        }
    }
    @Override
    public List<Product> findByName(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
    public List<Product> findByPriceBetween(Long minPrice, Long maxPrice) {
        return repository.findByPriceBetween(minPrice,maxPrice);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return repository.findByCategory(category);
    }

    @Override
    public List<Product> searchProducts(String name, Long minPrice, Long maxPrice, String category) {
        return repository.searchProducts(name,minPrice,maxPrice, category);
    }


}
