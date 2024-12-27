package com.orderiFy.app.orderModule.controller;

import com.orderiFy.app.orderModule.dto.OrderItemDto;
import com.orderiFy.app.orderModule.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    // This endpoint gets all order items by the order ID
    @GetMapping("/{orderId}")
    public List<OrderItemDto> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    // This endpoint gets a single order item by its ID
    @GetMapping("/item/{orderItemId}")
    public OrderItemDto getOrderItemById(@PathVariable Long orderItemId){
        return orderItemService.getOrderItemById(orderItemId);
    }

    @PostMapping("/")
    public OrderItemDto createOrderItem(@RequestBody OrderItemDto orderItemDto) {
        return orderItemService.createOrderItem(orderItemDto);
    }

    @PostMapping("/batch")
    public ResponseEntity<?> saveOrderItems(@RequestBody List<OrderItemDto> orderItems) {
        for (OrderItemDto orderItemDto : orderItems) {
            if (orderItemDto.getOrderId() == null) {
                return ResponseEntity.badRequest().body("Order ID is required.");
            }
            // save logic here, for example:
            orderItemService.createOrderItem(orderItemDto);
        }
        return ResponseEntity.ok("Order items saved successfully!");
    }


    @PutMapping("/{orderId}")
    public OrderItemDto updateOrderItem(@PathVariable Long orderItemId, @RequestBody OrderItemDto orderItemDto) {
        return orderItemService.updateOrderItem(orderItemId, orderItemDto);
    }

    @PutMapping("/{orderId}/batch")
    public List<OrderItemDto> updateOrderItems(@RequestBody UpdateOrderItemsRequest request) {
        return orderItemService.updateOrderItems(request.getOrderItemIds(), request.getOrderItemDtos());
    }

    @DeleteMapping("/delete/{orderItemId}")
    public ResponseEntity<String> removeOrderItem(@PathVariable Long orderItemId) {
        try {
            orderItemService.removeOrderItem(orderItemId);
            return ResponseEntity.ok("Order item removed successfully.");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @Data
    public static class UpdateOrderItemsRequest {
        private List<Long> orderItemIds;
        private List<OrderItemDto> orderItemDtos;

    }


    @DeleteMapping("/{orderItemId}")
    public void deleteOrderItem(@PathVariable Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
    }

    @DeleteMapping("/")
    public void deleteOrderItems(@RequestBody List<Long> orderItemIds) {
        orderItemService.deleteOrderItems(orderItemIds);
    }
}
