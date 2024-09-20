package com.project.furniture.model.order;

import com.project.furniture.model.BaseEntity;
import com.project.furniture.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone_number;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(length = 100)
    private String note;

    @CreationTimestamp
    private Date order_date;

    @Column(length = 20)
    private String status;

    private String total_money;
    private String shipping_method;
    private String shipping_address;
    private String shipping_date;
    private String tracking_number;
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
}
