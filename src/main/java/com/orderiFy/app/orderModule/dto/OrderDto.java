package com.orderiFy.app.orderModule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orderiFy.app.framework.util.Enums;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



@Data
public class OrderDto {
  private Long orderId;
  private Long customerId;
  private String customerName;
  private String orderNumber;
  private Long orderTakenByUserId;
  private String orderTakenByUsername;
  private Double totalAmount;
  private Enums.OrderPriority orderPriority;
  private Enums.OrderStatus orderStatus;
  private Boolean isDeleted;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate dueDate;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate orderTakenDate;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime createdAt;

  private Long createdBy;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime updatedAt;

  private Long updatedBy;

  private List<OrderItemDto> orderItems;
}

