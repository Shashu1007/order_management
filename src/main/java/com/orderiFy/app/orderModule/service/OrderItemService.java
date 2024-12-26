package com.orderiFy.app.orderModule.service;

import com.orderiFy.app.orderModule.dto.OrderItemDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public interface    OrderItemService {
    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    OrderItemDto getOrderItemById(Long orderItemId);

    OrderItemDto createOrderItem(OrderItemDto orderItemDto);

    @Transactional
    List<OrderItemDto> createOrderItems(List<OrderItemDto> orderItemDtos);

    OrderItemDto updateOrderItem(Long orderItemId, OrderItemDto orderItemDto);

    void deleteOrderItems(List<Long> ids);


    void deleteOrderItem(Long orderItemId);
}
