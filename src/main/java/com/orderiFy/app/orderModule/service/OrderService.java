package com.orderiFy.app.orderModule.service;


import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    OrderDto createOrder(OrderDto dto);


    Order getOrderById(Long id);

    Page<OrderDto> getAllOrders(String keyword, int page, int  size, String  sortBy, String sortDir);
    OrderDto updateOrder(Long id, OrderDto dto);
    void deleteOrder(Long id);

    String getNextOrderNumber() ;
    void deleteOrders(List<Long> ids);


    Page<OrderDto> findAllOrders(int page, int size,String sortBy,String sortDir);

    OrderDto getOrderByIdAndIsDeletedFalse(long id) ;

}
