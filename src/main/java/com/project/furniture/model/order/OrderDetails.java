package com.project.furniture.model.order;

import com.project.furniture.model.BaseEntity;
import com.project.furniture.model.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")
@Builder
public class OrderDetails extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;
    private int number_of_products;
    private Long total_money;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

}
