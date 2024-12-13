package com.orderiFy.app.orderModule.dto;

import com.orderiFy.app.framework.util.Enums;
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
  private Double totalAmount;
  private LocalDateTime dueDate;
  private String orderStatus;



}
