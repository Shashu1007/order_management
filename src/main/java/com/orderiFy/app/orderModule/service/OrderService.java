package com.orderiFy.app.orderModule.service;

import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface OrderService {

    @Transactional
    OrderDto createOrder(OrderDto dto);

    Order getOrderById(Long id);

    Page<OrderDto> getAllOrders(String keyword, int page, int size, String sortBy, String sortDir);

    OrderDto updateOrder(Long id, OrderDto dto);

    void deleteOrder(Long id);

    void deleteOrders(List<Long> ids);

    Page<OrderDto> findAllOrders(String keyword, int page, int size, String sortBy, String sortDir);

    OrderDto getOrderByIdAndIsDeletedFalse(long id);
}
