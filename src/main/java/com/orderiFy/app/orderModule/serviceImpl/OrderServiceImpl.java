package com.orderiFy.app.orderModule.serviceImpl;

import com.orderiFy.app.exception.ResourceNotFoundException;
import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.entity.OrderItems;
import com.orderiFy.app.orderModule.mappers.OrderMapper;
import com.orderiFy.app.orderModule.repository.OrderItemRepository;
import com.orderiFy.app.orderModule.repository.OrderRepository;
import com.orderiFy.app.orderModule.service.OrderService;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        order = orderRepository.save(order);

        return orderMapper.toDTO(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findByOrderIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
    }

    @Override
    public Page<OrderDto> getAllOrders(String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Order.asc(sortBy));
        if ("desc".equalsIgnoreCase(sortDir)) {
            sort = Sort.by(Sort.Order.desc(sortBy));
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findByKeyword(keyword, pageRequest);
        return orders.map(orderMapper::toDTO);
    }

    @Transactional
    @Override
    public OrderDto updateOrder(Long id, OrderDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        // Update the order entity with the values from the DTO
        order = orderMapper.updateEntityFromDTO(dto, order);

        // Save the updated order entity
        order = orderRepository.save(order);

        // Return the updated order as a DTO
        return orderMapper.toDTO(order);
    }


    @Override
    public void deleteOrder(Long id) {
        orderRepository.safeDeleteOrder(id);
    }

    @Override
    public void deleteOrders(List<Long> ids) {
        orderRepository.safeDeleteOrders(ids);
    }

    @Override
    public Page<OrderDto> findAllOrders(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Order.asc(sortBy));
        if ("desc".equalsIgnoreCase(sortDir)) {
            sort = Sort.by(Sort.Order.desc(sortBy));
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findAll(pageRequest);
        return orders.map(orderMapper::toDTO);
    }

    @Override
    public OrderDto getOrderByIdAndIsDeletedFalse(long id) {
        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
        return orderMapper.toDTO(order);
    }
}
