package com.project.furniture.model.product;

import com.project.furniture.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_images")
@Builder
public class ProuductImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
