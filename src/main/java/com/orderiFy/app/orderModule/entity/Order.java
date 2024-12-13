package com.orderiFy.app.orderModule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.framework.util.Enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "orders")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false, updatable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_number", nullable = false, updatable = false)
    private String orderNumber;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "order_taken_by_user_id", nullable = false)
    private Long orderTakenByUserId;

    @Column(name = "order_taken_by_username", nullable = false)
    private String orderTakenByUsername;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name="order_priority")
    private OrderPriority orderPriority = OrderPriority.LOW;
    
    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus = OrderStatus.NEW;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "created_by", updatable = false)
    private Long createdBy;


    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")

    private LocalDateTime updatedAt;



    @PostPersist
    public void setOrderNumber() {
        if (this.orderId != null) {
            this.orderNumber = "OID" + this.orderId;
        }
    }


}
