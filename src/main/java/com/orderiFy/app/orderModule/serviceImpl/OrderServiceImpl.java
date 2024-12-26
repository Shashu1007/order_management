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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);  // Logger instance

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
        logger.info("Creating a new order: {}", dto);  // Log order creation

        Order order = orderMapper.toEntity(dto);
        order = orderRepository.save(order);

        logger.info("Order created with ID: {}", order.getOrderId());  // Log after order is saved

        return orderMapper.toDTO(order);
    }

    @Override
    public Order getOrderById(Long id) {
        logger.info("Fetching order by ID: {}", id);  // Log when fetching order by ID

        return orderRepository.findByOrderIdAndIsDeletedFalse(id)
                .orElseThrow(() -> {
                    logger.error("Order not found with ID: {}", id);  // Log error when order is not found
                    return new ResourceNotFoundException("Order not found with ID: " + id);
                });
    }

    @Override
    public Page<OrderDto> getAllOrders(String keyword, int page, int size, String sortBy, String sortDir) {
        logger.info("Fetching all orders with keyword: {}, page: {}, size: {}, sortBy: {}, sortDir: {}", keyword, page, size, sortBy, sortDir);

        Sort sort = Sort.by(Sort.Order.asc(sortBy));
        if ("desc".equalsIgnoreCase(sortDir)) {
            sort = Sort.by(Sort.Order.desc(sortBy));
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findByKeyword(keyword, pageRequest);
        logger.info("Fetched {} orders", orders.getTotalElements());  // Log the number of orders fetched

        return orders.map(orderMapper::toDTO);
    }

    @Transactional
    @Override
    public OrderDto updateOrder(Long id, OrderDto dto) {
        logger.info("Updating order with ID: {}", id);  // Log when updating an order

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Order not found with ID: {}", id);  // Log error when order is not found
                    return new ResourceNotFoundException("Order not found with ID: " + id);
                });

        // Update the order entity with the values from the DTO
        order = orderMapper.updateEntityFromDTO(dto, order);

        // Save the updated order entity
        order = orderRepository.save(order);

        logger.info("Order updated with ID: {}", order.getOrderId());  // Log after order is updated

        // Return the updated order as a DTO
        return orderMapper.toDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        logger.info("Deleting order with ID: {}", id);  // Log when deleting an order
        orderRepository.safeDeleteOrder(id);
        logger.info("Order deleted with ID: {}", id);  // Log after order is deleted
    }

    @Override
    public void deleteOrders(List<Long> ids) {
        logger.info("Deleting multiple orders with IDs: {}", ids);  // Log when deleting multiple orders
        orderRepository.safeDeleteOrders(ids);
        logger.info("Orders deleted with IDs: {}", ids);  // Log after orders are deleted
    }

    @Override
    public Page<OrderDto> findAllOrders(String keyword, int page, int size, String sortBy, String sortDir) {
        logger.info("Fetching all orders with keyword: {}, page: {}, size: {}, sortBy: {}, sortDir: {}", keyword, page, size, sortBy, sortDir);

        Sort sort = Sort.by(Sort.Order.asc(sortBy));
        if ("desc".equalsIgnoreCase(sortDir)) {
            sort = Sort.by(Sort.Order.desc(sortBy));
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findAll(pageRequest);
        logger.info("Fetched {} orders", orders.getTotalElements());  // Log the number of orders fetched

        return orders.map(orderMapper::toDTO);
    }

    @Override
    public OrderDto getOrderByIdAndIsDeletedFalse(long id) {
        logger.info("Fetching order by ID (non-deleted): {}", id);  // Log when fetching order by ID and not deleted

        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(id)
                .orElseThrow(() -> {
                    logger.error("Order not found with ID: {}", id);  // Log error when order is not found
                    return new ResourceNotFoundException("Order not found with ID: " + id);
                });

        return orderMapper.toDTO(order);
    }
}
