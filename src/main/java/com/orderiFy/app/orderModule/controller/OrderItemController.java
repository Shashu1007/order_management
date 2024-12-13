package com.orderiFy.app.orderModule.controller;

import com.orderiFy.app.orderModule.dto.OrderItemDto;
import com.orderiFy.app.orderModule.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{orderId}")
    public List<OrderItemDto> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId);
    }


    @PostMapping
    public OrderItemDto createOrderItem(@RequestBody OrderItemDto orderItemDto) {
        return orderItemService.createOrderItem(orderItemDto);
    }

    @PutMapping("/{orderItemId}")
    public OrderItemDto updateOrderItem(@PathVariable Long orderItemId, @RequestBody OrderItemDto orderItemDto) {
        return orderItemService.updateOrderItem(orderItemId, orderItemDto);
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
