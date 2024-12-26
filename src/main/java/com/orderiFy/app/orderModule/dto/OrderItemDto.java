package com.orderiFy.app.orderModule.dto;

import com.orderiFy.app.productModule.dto.ProductDto; // Assuming the ProductDto exists
import com.fasterxml.jackson.annotation.JsonFormat;
import com.orderiFy.app.framework.util.Enums;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class OrderItemDto {
    private Long orderItemId;
    private Long orderId;

    private Long productId; // Product ID (could be kept if needed for backend or database purposes)

    private ProductDto product; // New field for ProductDto containing detailed product information

    private String productName; // Product name for display (could be derived from ProductDto)
    private int quantity;

    @Enumerated(value = EnumType.STRING)
    private Enums.Uom uom;

    private Double pricePerUnit;

    private Double totalAmount; // Can be calculated as quantity * pricePerUnit

    private Boolean isDeleted = false;

    private LocalDateTime createdAt;
    private String createdBy;

    private LocalDateTime updatedAt;
    private String updatedBy;
}
