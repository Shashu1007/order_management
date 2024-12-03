package com.orderiFy.app.orderModule.dto;

import com.orderiFy.app.customerModule.entity.Customer;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@ToString
@Builder
@Data
public class OrderDto {
  private Long orderId;
  private Customer customer;
  private String customerName;
  private Long orderTakenByUserId;
  private String orderTakenByUsername;
  private Double totalAmount;
  private LocalDateTime dueDate;
  private String orderStatus;
  private Boolean isDeleted;
  private String createdBy;
  private String updatedBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private List<OrderItemDto> orderItems;
}
