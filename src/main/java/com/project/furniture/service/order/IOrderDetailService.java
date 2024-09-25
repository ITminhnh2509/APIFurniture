package com.project.furniture.service.order;

import com.project.furniture.dto.order.OrderDetailDTO;
import com.project.furniture.exception.DataNotFoundException;
import com.project.furniture.model.order.Order;
import com.project.furniture.model.order.OrderDetails;

import java.util.List;

public interface IOrderDetailService {
    OrderDetails createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetails getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetails updateOrderDetail(Long id,OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    void deleteById(Long id) ;
    List<OrderDetails> findByOrderId(Long orderId);
    List<OrderDetails> findAllOrdersDetail();
}
