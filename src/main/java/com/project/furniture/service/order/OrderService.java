package com.project.furniture.service.order;

import com.project.furniture.dto.order.OrderDTO;
import com.project.furniture.model.order.Order;
import com.project.furniture.model.user.User;
import com.project.furniture.repository.user.UserRepository;
import com.project.furniture.response.order.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        // Create a new Order object from the OrderDTO
         User user = userRepository.findById(orderDTO.getUserId())
                 .orElseThrow(() -> new Exception("User not found"));


        Order order = Order.builder()
                .fullname(orderDTO.getFullname())
                .email(orderDTO.getEmail())
                .phone_number(orderDTO.getPhone_number())
                .address(orderDTO.getAddress())
                .note(orderDTO.getNote())
                .status(orderDTO.getStatus())
                .total_money(orderDTO.getTotal_money())
                .shipping_method(orderDTO.getShipping_method())
                .shipping_address(orderDTO.getShipping_address())
                .shipping_date(orderDTO.getShipping_date()) // Ensure this is properly set
                .tracking_number(orderDTO.getTracking_number())
                .paymentMethod(orderDTO.getPaymentMethod())

                .user(user)

                .build();
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            // Handle specific exception if necessary
            throw new Exception("Error while creating the order: " + e.getMessage(), e);
        }
    }


    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataAccessException {
        // Fetch the existing order from the repository
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Update the fields of the existing order with data from the OrderDTO
        existingOrder.setFullname(orderDTO.getFullname());
        existingOrder.setEmail(orderDTO.getEmail());
        existingOrder.setPhone_number(orderDTO.getPhone_number());
        existingOrder.setAddress(orderDTO.getAddress());
        existingOrder.setNote(orderDTO.getNote());
        existingOrder.setStatus(orderDTO.getStatus());
        existingOrder.setTotal_money(orderDTO.getTotal_money());
        existingOrder.setShipping_method(orderDTO.getShipping_method());
        existingOrder.setShipping_address(orderDTO.getShipping_address());
        existingOrder.setShipping_date(orderDTO.getShipping_date());
        existingOrder.setTracking_number(orderDTO.getTracking_number());
        existingOrder.setPaymentMethod(orderDTO.getPaymentMethod());

        // Save the updated order
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        // Check if the order exists before attempting to delete
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }

        try {
            orderRepository.deleteById(id);
        } catch (DataAccessException e) {
            // Handle the exception as needed
            throw new RuntimeException("Error while deleting the order: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
