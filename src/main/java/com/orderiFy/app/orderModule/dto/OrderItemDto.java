package com.orderiFy.app.orderModule.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
public class OrderItemDto {
    private Long orderItemId;
    private Long orderId;  // You can use this or the entire OrderDto
    private Long productId;  // For the product reference
    private String productName;
    private int quantity;
    private String uom;
    private Double pricePerUnit;
    private Double totalAmount;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;


}
