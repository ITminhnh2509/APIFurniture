package com.project.furniture.model.product;

import com.project.furniture.model.BaseEntity;
import com.project.furniture.model.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Long price;

    private String description;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

     @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
     private List<ProuductImage> productImages;
}
