package com.orderiFy.app.orderModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;




@Data
public class OrderDto {
  private Long orderId;
  private String orderNumber;
  private String customerName;
  private String orderTakenByUsername;
  private Double totalAmount;
  private LocalDateTime dueDate;
  private String orderStatus;
  private List<OrderItemDto> orderItems;


}
