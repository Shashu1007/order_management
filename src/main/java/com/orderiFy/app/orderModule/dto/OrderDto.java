  package com.orderiFy.app.orderModule.dto;

  import com.fasterxml.jackson.annotation.JsonFormat;
  import com.orderiFy.app.customerModule.entity.Customer;
  import com.orderiFy.app.framework.util.Enums;
  import jakarta.persistence.EnumType;
  import jakarta.persistence.Enumerated;
  import lombok.Data;
  import lombok.Getter;
  import lombok.Setter;
  import org.springframework.format.annotation.DateTimeFormat;

  import java.time.LocalDate;
  import java.time.LocalDateTime;


  @Data // Lombok will generate getters, setters, toString, equals, and hashCode methods
  public class OrderDto {

    private Long customerId; // To hold customerId
    private String customerName; // To hold customerName
    private String orderNumber;
    private Long orderTakenByUserId;
    private String orderTakenByUsername;
    private Double totalAmount;


    @Getter
    @Setter
    @Enumerated(EnumType.STRING) // Not necessary for DTO, only for entity
    private Enums.OrderPriority orderPriority;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dueDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate orderTakenDate;
  }



