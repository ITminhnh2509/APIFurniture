package com.project.furniture.service.order;

import com.project.furniture.dto.order.OrderDTO;
import com.project.furniture.model.order.Order;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order getOrder(Long id)  ;
    Order updateOrder(Long id,OrderDTO orderDTO) throws DataAccessException;
    void deleteOrder(Long id);
    List<Order> findByUserId(Long userId);
    List<Order> findAllOrders();
}
