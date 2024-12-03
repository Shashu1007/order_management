package com.orderiFy.app.orderModule.dto;

import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.productModule.entity.Product;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderItemDto {
    private Long orderItemId;
    private Order orderId;
    private Product productId;
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
