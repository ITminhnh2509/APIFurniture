package com.project.furniture.controller.admin;

import com.project.furniture.dto.order.OrderDTO;
import com.project.furniture.model.order.Order;
import com.project.furniture.model.product.Product;
import com.project.furniture.response.ApiResponse;
import com.project.furniture.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
public class OrderAdminController {
    private final OrderService orderService;
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody OrderDTO orderDTO) throws Exception{
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(orderService.createOrder(orderDTO))
                .message("add successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(
            @Valid @PathVariable("id") Long id){
        try{
            Order existingOrder=orderService.getOrder(id);
            return ResponseEntity.ok().body(existingOrder);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getAllOrders(
            @Valid @PathVariable("user_id") Long userId){
        try{
            List<Order> orders=orderService.findByUserId(userId);
            return ResponseEntity.ok().body(orders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody OrderDTO orderDTO){

        try{
            Order order=orderService.updateOrder(id,orderDTO);
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(order)
                    .message("add successfully")
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(apiResponse);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        //change active
        orderService.deleteOrder(id);
        return ResponseEntity.ok("order delete"+id);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(orderService.findAllOrders())
                .message("get all successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
