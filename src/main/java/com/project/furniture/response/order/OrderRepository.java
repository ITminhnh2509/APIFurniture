package com.project.furniture.response.order;

import com.project.furniture.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long  userId);

}
