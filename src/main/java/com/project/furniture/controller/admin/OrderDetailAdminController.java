package com.project.furniture.controller.admin;

import com.project.furniture.dto.order.OrderDTO;
import com.project.furniture.dto.order.OrderDetailDTO;
import com.project.furniture.exception.DataNotFoundException;
import com.project.furniture.model.order.Order;
import com.project.furniture.model.order.OrderDetails;
import com.project.furniture.response.ApiResponse;
import com.project.furniture.service.order.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orderdetail")
@RequiredArgsConstructor
public class OrderDetailAdminController {
    private final OrderDetailService orderDetailService;
    @PostMapping("/add")
    public ResponseEntity<?> addOrderDetail(@RequestBody OrderDetailDTO orderDTO) throws Exception{
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(orderDetailService.createOrderDetail(orderDTO))
                .message("add successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(
            @Valid @PathVariable("id") Long id){
        try{
            OrderDetails existingOrder=orderDetailService.getOrderDetail(id);
            return ResponseEntity.ok().body(existingOrder);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getAllOrders(
            @Valid @PathVariable("order_id") Long userId){
        try{
            List<OrderDetails> orders=orderDetailService.findByOrderId(userId);
            return ResponseEntity.ok().body(orders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody OrderDetailDTO orderDTO){

        try{
            OrderDetails order=orderDetailService.updateOrderDetail(id,orderDTO);
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(order)
                    .message("up successfully")
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(apiResponse);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        orderDetailService.deleteById(id);
        return ResponseEntity.ok("delete"+id);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(orderDetailService.findAllOrdersDetail())
                .message("get all successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
