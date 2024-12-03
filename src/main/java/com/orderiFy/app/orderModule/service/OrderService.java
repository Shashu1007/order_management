package com.orderiFy.app.orderModule.service;


import com.orderiFy.app.orderModule.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderDto createOrder(OrderDto dto);
    OrderDto getOrderById(Long id);
    List<OrderDto> getAllOrders();
    OrderDto updateOrder(Long id, OrderDto dto);
    void deleteOrder(Long id);
}
