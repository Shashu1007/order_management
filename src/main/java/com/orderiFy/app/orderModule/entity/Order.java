package com.orderiFy.app.orderModule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.framework.util.Enums;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_taken_by_user_id")
    private Long orderTakenByUserId;

    @Column(name = "order_taken_by_username")
    private String orderTakenByUsername;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_priority")
    private Enums.OrderPriority orderPriority;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private Enums.OrderStatus orderStatus;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "order_taken_date")
    private LocalDate orderTakenDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems;
}
