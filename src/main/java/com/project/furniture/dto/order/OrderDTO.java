package com.project.furniture.dto.order;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String fullname;
    private String email;
    private String phone_number;
    private String address;
    private String note;
    private String status;
    private String total_money;
    private String shipping_method;
    private String shipping_address;
    private String shipping_date;
    private String tracking_number;
    private String paymentMethod;
    private List<OrderDetailDTO> orderDetails;
}
