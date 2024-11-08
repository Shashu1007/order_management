package com.orderiFy.app.service;


import com.orderiFy.app.dto.OrderDto;
import com.orderiFy.app.exception.ResourceNotFoundException;
import com.orderiFy.app.model.Orders;
import com.orderiFy.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderDto getOrderById(int orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return convertToDto(order);
    }

    private OrderDto convertToDto(Orders order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setCustomerId(order.getCustomer().getCustomerId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setStatus(String.valueOf(order.getStatus()));
        orderDto.setTotalAmount(order.getTotalAmount());
        return orderDto;
    }
}