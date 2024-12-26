package com.orderiFy.app.orderModule.mappers;

import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.entity.OrderItems;
import com.orderiFy.app.orderModule.dto.OrderItemDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final OrderItemsMapper orderItemsMapper;

    public OrderMapper(OrderItemsMapper orderItemsMapper) {
        this.orderItemsMapper = orderItemsMapper;
    }

    // Convert Order entity to OrderDto
    public OrderDto toDTO(Order order) {
        if (order == null) {
            return null;
        }

        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getOrderId());

        // Map Customer fields
        if (order.getCustomer() != null) {
            dto.setCustomerId(order.getCustomer().getCustomerId());
            dto.setCustomerName(order.getCustomer().getCustomerName());
        }

        // Map Order fields
        dto.setOrderNumber(order.getOrderNumber());
        dto.setOrderTakenByUserId(order.getOrderTakenByUserId());
        dto.setOrderTakenByUsername(order.getOrderTakenByUsername());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderPriority(order.getOrderPriority());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setIsDeleted(order.getIsDeleted());
        dto.setDueDate(order.getDueDate());
        dto.setOrderTakenDate(order.getOrderTakenDate());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setCreatedBy(order.getCreatedBy());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setUpdatedBy(order.getUpdatedBy());

        // Map OrderItems list
        if (order.getOrderItems() != null) {
            dto.setOrderItems(order.getOrderItems().stream()
                    .map(orderItemsMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    // Convert OrderDto to Order entity
    public Order toEntity(OrderDto dto) {
        if (dto == null) {
            return null;
        }

        Order order = new Order();
        order.setOrderId(dto.getOrderId());

        // Map Customer fields
        if (dto.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setCustomerId(dto.getCustomerId());
            order.setCustomer(customer);
        }

        // Map Order fields
        order.setOrderNumber(dto.getOrderNumber());
        order.setOrderTakenByUserId(dto.getOrderTakenByUserId());
        order.setOrderTakenByUsername(dto.getOrderTakenByUsername());
        order.setTotalAmount(dto.getTotalAmount());
        order.setOrderPriority(dto.getOrderPriority());
        order.setOrderStatus(dto.getOrderStatus());
        order.setIsDeleted(dto.getIsDeleted());
        order.setDueDate(dto.getDueDate());
        order.setOrderTakenDate(dto.getOrderTakenDate());
        order.setCreatedAt(dto.getCreatedAt());
        order.setCreatedBy(dto.getCreatedBy());
        order.setUpdatedAt(dto.getUpdatedAt());
        order.setUpdatedBy(dto.getUpdatedBy());

        // Map OrderItems list
        if (dto.getOrderItems() != null) {
            order.setOrderItems(dto.getOrderItems().stream()
                    .map(orderItemsMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        return order;
    }

    // Update existing Order entity with OrderDto values
    public void updateEntity(OrderDto dto, Order entity) {
        if (dto == null || entity == null) {
            return;
        }

        // Update Customer fields
        if (dto.getCustomerId() != null) {
            if (entity.getCustomer() == null) {
                entity.setCustomer(new Customer());
            }
            entity.getCustomer().setCustomerId(dto.getCustomerId());
        }

        if (dto.getCustomerName() != null && entity.getCustomer() != null) {
            entity.getCustomer().setCustomerName(dto.getCustomerName());
        }

        // Update Order fields
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setOrderTakenByUserId(dto.getOrderTakenByUserId());
        entity.setOrderTakenByUsername(dto.getOrderTakenByUsername());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setOrderPriority(dto.getOrderPriority());
        entity.setOrderStatus(dto.getOrderStatus());
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setDueDate(dto.getDueDate());
        entity.setOrderTakenDate(dto.getOrderTakenDate());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setUpdatedBy(dto.getUpdatedBy());

        // Update OrderItems list
        if (dto.getOrderItems() != null) {
            entity.setOrderItems(dto.getOrderItems().stream()
                    .map(orderItemsMapper::toEntity)
                    .collect(Collectors.toList()));
        }
    }
    // In OrderMapper class
    public Order updateEntityFromDTO(OrderDto dto, Order entity) {
        if (dto == null || entity == null) {
            return null;
        }

        // Update Customer fields
        if (dto.getCustomerId() != null) {
            if (entity.getCustomer() == null) {
                entity.setCustomer(new Customer());
            }
            entity.getCustomer().setCustomerId(dto.getCustomerId());
        }

        if (dto.getCustomerName() != null && entity.getCustomer() != null) {
            entity.getCustomer().setCustomerName(dto.getCustomerName());
        }

        // Update Order fields
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setOrderTakenByUserId(dto.getOrderTakenByUserId());
        entity.setOrderTakenByUsername(dto.getOrderTakenByUsername());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setOrderPriority(dto.getOrderPriority());
        entity.setOrderStatus(dto.getOrderStatus());
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setDueDate(dto.getDueDate());
        entity.setOrderTakenDate(dto.getOrderTakenDate());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setUpdatedBy(dto.getUpdatedBy());

        // Update OrderItems list
        if (dto.getOrderItems() != null) {
            entity.setOrderItems(dto.getOrderItems().stream()
                    .map(orderItemsMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        return entity; // Return the updated entity
    }



}
