package com.project.furniture.service.order;

import com.project.furniture.dto.order.OrderDetailDTO;
import com.project.furniture.exception.DataNotFoundException;
import com.project.furniture.model.order.Order;
import com.project.furniture.model.order.OrderDetails;
import com.project.furniture.model.product.Product;
import com.project.furniture.repository.product.ProductRepository;
import com.project.furniture.response.order.OrderDetailRepository;
import com.project.furniture.response.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements  IOrderDetailService{
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDetails createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order=orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        Product product=productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));;
        OrderDetails orderDetail=OrderDetails.builder()
                .order(order)
                .product(product)
                .number_of_products(orderDetailDTO.getNumber_of_products())
                .price(orderDetailDTO.getPrice())
                .total_money(orderDetailDTO.getTotal_money())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetails getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Order Not found"));
    }

    @Override
    public OrderDetails updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetails existingOrderDetail=orderDetailRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Order detail Not found"));
        Order existingOrder=orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Order Not found"));
        Product existingProduct=productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Product not found"));
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        existingOrderDetail.setNumber_of_products(orderDetailDTO.getNumber_of_products());
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setTotal_money(orderDetailDTO.getTotal_money());
        return  orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetails> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderDetails> findAllOrdersDetail() {
        return orderDetailRepository.findAll();
    }


}
