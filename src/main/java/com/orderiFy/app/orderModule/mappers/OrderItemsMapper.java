package com.orderiFy.app.orderModule.mappers;

import com.orderiFy.app.exception.ResourceNotFoundException;
import com.orderiFy.app.framework.util.Enums;
import com.orderiFy.app.orderModule.dto.OrderItemDto;
import com.orderiFy.app.orderModule.entity.OrderItems;
import com.orderiFy.app.productModule.entity.Product;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.productModule.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemsMapper {
    @Autowired
    private ProductRepository productRepository;



    // Convert OrderItems entity to OrderItemDto
    public OrderItemDto toDTO(OrderItems orderItems) {
        if (orderItems == null) {
            return null;
        }

        OrderItemDto dto = new OrderItemDto();
        dto.setOrderItemId(orderItems.getOrderItemId());

        // Map Order ID
        if (orderItems.getOrder() != null) {
            dto.setOrderId(orderItems.getOrder().getOrderId());
        }

        // Map Product details
        if (orderItems.getProduct() != null) {
            dto.setProductId(orderItems.getProduct().getProductId());
            dto.setProductName(orderItems.getProduct().getProductName());
        }

        dto.setQuantity(orderItems.getQuantity());
        dto.setUom(orderItems.getUom() != null ? Enums.Uom.valueOf(orderItems.getUom()) : null);
        dto.setPricePerUnit(orderItems.getPricePerUnit());
        dto.setTotalAmount(orderItems.getTotalAmount());
        dto.setIsDeleted(orderItems.getIsDeleted());
        dto.setCreatedAt(orderItems.getCreatedAt());
        dto.setCreatedBy(orderItems.getCreatedBy());
        dto.setUpdatedAt(orderItems.getUpdatedAt());
        dto.setUpdatedBy(orderItems.getUpdatedBy());

        return dto;
    }

    // Convert OrderItemDto to OrderItems entity
    public OrderItems toEntity(OrderItemDto dto) {
        if (dto == null) {
            return null;
        }

        OrderItems orderItems = new OrderItems();
        orderItems.setOrderItemId(dto.getOrderItemId());

        // Map Order
        if (dto.getOrderId() != null) {
            Order order = new Order();
            order.setOrderId(dto.getOrderId());
            orderItems.setOrder(order);
        }

        // Map Product
        if (dto.getProductId() != null) {
            Product product = new Product();
            product.setProductId(dto.getProductId());
            orderItems.setProduct(product);
        }

        orderItems.setQuantity(dto.getQuantity());
        orderItems.setUom(String.valueOf(dto.getUom()));
        orderItems.setPricePerUnit(dto.getPricePerUnit());
        orderItems.setTotalAmount(dto.getTotalAmount());
        orderItems.setIsDeleted(dto.getIsDeleted());
        orderItems.setCreatedAt(dto.getCreatedAt());
        orderItems.setCreatedBy(dto.getCreatedBy());
        orderItems.setUpdatedAt(dto.getUpdatedAt());
        orderItems.setUpdatedBy(dto.getUpdatedBy());

        return orderItems;
    }

    // Update an existing OrderItems entity with values from OrderItemDto
    public void updateEntity(OrderItemDto dto, OrderItems entity) {
        if (dto == null || entity == null) {
            return;
        }

        // Update Product if provided
        if (dto.getProductId() != null) {
            if (entity.getProduct() == null) {
                entity.setProduct(new Product());
            }
            entity.getProduct().setProductId(dto.getProductId());
        }

        // Update Order if provided
        if (dto.getOrderId() != null) {
            if (entity.getOrder() == null) {
                entity.setOrder(new Order());
            }
            entity.getOrder().setOrderId(dto.getOrderId());
        }

        // Update other fields
        entity.setQuantity(dto.getQuantity());
        entity.setUom(dto.getUom() != null ? String.valueOf(dto.getUom()) : entity.getUom());
        entity.setPricePerUnit(dto.getPricePerUnit());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setUpdatedBy(dto.getUpdatedBy());
    }

    // In OrderItemsMapper class
    public OrderItems updateEntityFromDTO(OrderItemDto dto, OrderItems entity) {
        if (dto == null || entity == null) {
            return null; // Return null if DTO or entity is null
        }

        // Update product information (if needed)
        if (dto.getProductId() != null) {
            // Assuming a method exists to fetch the product by ID
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + dto.getProductId()));
            entity.setProduct(product); // Set the product if present
        }

        // Update other fields
        entity.setQuantity(dto.getQuantity());
        entity.setUom(String.valueOf(dto.getUom()));
        entity.setPricePerUnit(dto.getPricePerUnit());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setUpdatedBy(dto.getUpdatedBy());

        return entity; // Return the updated entity
    }

}
