package com.project.furniture.dto.order;

import com.project.furniture.model.order.Order;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Long price;
    private int number_of_products;
    private Long total_money;
    private Order order;

    //hai them
    private Long orderId;
    private Long productId;
}


