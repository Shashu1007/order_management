package com.orderiFy.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long orderId;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime orderDate;
    private String status;
    private double totalAmount;
    private Date createdAt;
    private Date updatedAt;
    private Long createdBy;
    private Long UpdatedBy;

}