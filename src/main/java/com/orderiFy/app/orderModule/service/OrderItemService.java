package com.orderiFy.app.orderModule.service;

import com.orderiFy.app.orderModule.dto.OrderItemDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface    OrderItemService {
    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    OrderItemDto GetOrderItemById(Long orderItemId);

    OrderItemDto createOrderItem(OrderItemDto orderItemDto);
    OrderItemDto updateOrderItem(Long orderItemId, OrderItemDto orderItemDto);

    void deleteOrderItems(List<Long> ids);



    void deleteOrderItem(Long orderItemId);
}
