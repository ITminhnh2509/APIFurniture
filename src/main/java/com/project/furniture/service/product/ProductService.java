package com.project.furniture.service.product;

import com.project.furniture.dto.product.ProductImageDTO;
import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.Product;
import com.project.furniture.model.product.ProductImage;
import com.project.furniture.repository.category.CategoryRepository;
import com.project.furniture.repository.product.ProductImageRepository;
import com.project.furniture.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
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
        Category category = categoryRepository.findById(dto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Id Not Found"));
//        category.setName(dto.getName());
        dto.setCategory(category);
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
    public List<Product> searchProducts(String name, Long minPrice, Long maxPrice) {
        return repository.searchProducts(name,minPrice,maxPrice);
    }

    @Override
    public List<ProductImage> getAll(Long id) {
        return productImageRepository.findByProductId(id);
    }

    @Override
    public ProductImage getProductImageById(Long id) {
        return productImageRepository.findById(id).orElseThrow(() -> new RuntimeException("Id Not Found"));
    }

    @Override
    public ProductImage create(Long id,ProductImageDTO productImage) {
        Product product = getById(id);
        ProductImage productImage1 =  ProductImage.builder()
                .product(product)
                .image_url(productImage.getImage_url())
                .build();

        return productImageRepository.save(productImage1);
    }

    @Override
    public void removeProductImage(Long id) {
        ProductImage productImage1 = productImageRepository.findById(id).orElseThrow(() -> new RuntimeException("Id Not Found"));
        if (productImage1 != null){
            productImage1.setImage_url(productImage1.getImage_url());
        }
        productImageRepository.deleteById(id);
    }
    //admin luu hinh
    @Override
    public ProductImage saveProductImage(Long productId, ProductImageDTO productImageDTO) {
        Product product=getById(productId);
        ProductImage productImage=ProductImage.builder()
                .product(product)
                .image_url(productImageDTO.getImage_url())
                .build();
        int size=productImageRepository.findByProductId(productId).size();
        if(size>=4)
            throw new InvalidParameterException("mỗi sinh viên tối đa 4 hình");

        return productImageRepository.save(productImage);
    }


}
