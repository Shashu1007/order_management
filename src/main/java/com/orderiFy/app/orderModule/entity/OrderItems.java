package com.orderiFy.app.orderModule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orderiFy.app.productModule.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Session;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;





@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_items")
@ToString(exclude = {"order"})
@EntityListeners(AuditingEntityListener.class)
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "uom")
    private String uom;

    @Column(name = "price_per_unit")
    private Double pricePerUnit;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "is_deleted")
    private Boolean isDeleted=false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
