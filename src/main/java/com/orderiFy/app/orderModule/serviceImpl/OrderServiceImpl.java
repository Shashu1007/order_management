package com.orderiFy.app.orderModule.serviceImpl;

import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.customerModule.exceptions.CustomerNotFoundException;
import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.mappers.OrderMapper;
import com.orderiFy.app.orderModule.repository.OrderRepository;
import com.orderiFy.app.orderModule.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final HttpSession session;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper ,HttpSession session) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.session=session;
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedBy(session.getAttribute("username").toString());
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }



    @Override
    public OrderDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toDTO).toList();
    }


    @Override
    public OrderDto updateOrder(Long id, OrderDto dto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with order id : "+ id));
        Order updatedOrder = orderMapper.toEntity(dto);
        updatedOrder.setOrderId(existingOrder.getOrderId());
        updatedOrder.setUpdatedAt(LocalDateTime.now());
        updatedOrder.setUpdatedBy(session.getAttribute("username").toString());

        return orderMapper.toDTO(orderRepository.save(updatedOrder));
    }

    @Override
    public void deleteOrder(Long id) {


        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Order not found with id : " + id));

        order.setIsDeleted(true);
        order.setUpdatedBy(session.getAttribute("username").toString());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }

    @Override
    public OrderDto getOrderByIdAndIsDeletedFalse(long id) {

        return orderRepository.findByOrderIdAndIsDeletedFalse(id)
                .map(orderMapper::toDTO)
                .orElseThrow(()-> new RuntimeException("Order Not Found With order Id " + id));
    }

}

