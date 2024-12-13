package com.orderiFy.app.orderModule.serviceImpl;

import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.customerModule.exceptions.CustomerNotFoundException;
import com.orderiFy.app.customerModule.repository.CustomerRepository;
import com.orderiFy.app.exception.ResourceNotFoundException;
import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.mappers.OrderMapper;
import com.orderiFy.app.orderModule.repository.OrderRepository;
import com.orderiFy.app.orderModule.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository ;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.customerRepository=customerRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {
        logger.info("Creating a new order...");


            Order order = orderMapper.toEntity(dto);




        // Map OrderDto to Order entity
        order.setCreatedAt(LocalDateTime.now());

        // Generate the next order number
        order.setOrderNumber(getNextOrderNumber());

        // Save the order
        Order savedOrder = orderRepository.save(order);
        logger.info("Order created with ID: {}", savedOrder.getOrderId());

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public Order getOrderById(Long id) {
        logger.info("Fetching order with ID: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    @Override
    public Page<OrderDto> getAllOrders(String keyword, int page, int size, String sortBy, String sortDir) {
        logger.info("Fetching all orders with keyword: {}", keyword);

        // Handle sorting
        Sort sort = (sortDir != null && sortDir.equalsIgnoreCase("desc"))
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch paginated data with keyword search
        Page<Order> orderPage = orderRepository.findByKeyword(
                keyword != null ? keyword.trim() : "",
                pageable
        );

        logger.info("Total orders fetched: {}", orderPage.getTotalElements());

        // Map the orders to DTOs
        return orderPage.map(orderMapper::toDTO);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto dto) {
        logger.info("Updating order with ID: {}", id);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        Order updatedOrder = orderMapper.toEntity(dto);
        updatedOrder.setOrderId(existingOrder.getOrderId());
        updatedOrder.setUpdatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(updatedOrder);
        logger.info("Order updated with ID: {}", savedOrder.getOrderId());

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        logger.info("Deleting order with ID: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Order not found with ID: " + id));

        order.setIsDeleted(true);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        logger.info("Order soft-deleted with ID: {}", id);
    }

    @Override
    public String getNextOrderNumber() {
        logger.info("Fetching the next order number...");
        Optional<Order> latestOrder = orderRepository.findLatestOrder();

        String nextOrderNumber = "ORD001"; // Default starting number
        if (latestOrder.isPresent()) {
            String lastOrderNumber = latestOrder.get().getOrderNumber();
            int orderNum = Integer.parseInt(lastOrderNumber.substring(3));
            nextOrderNumber = String.format("ORD%03d", orderNum + 1);
        }

        logger.info("Next order number generated: {}", nextOrderNumber);
        return nextOrderNumber;
    }

    @Override
    public void deleteOrders(List<Long> ids) {
        logger.info("Deleting orders with IDs: {}", ids);

        if (ids.isEmpty()) {
            throw new RuntimeException("No IDs provided for deletion.");
        }

        // Soft delete orders by updating the isDeleted field
        orderRepository.safeDeleteOrders(ids);

        logger.info("Orders soft-deleted with IDs: {}", ids);
    }

    @Override
    public Page<OrderDto> findAllOrders(int page, int size, String sortBy, String sortDir) {
        logger.info("Fetching all orders with pagination...");

        // Handle sorting
        Sort sort = (sortDir != null && sortDir.equalsIgnoreCase("desc"))
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch paginated data
        Page<Order> orderPage = orderRepository.findAllOrders(pageable);

        logger.info("Total orders fetched: {}", orderPage.getTotalElements());

        // Map the orders to DTOs
        return orderPage.map(orderMapper::toDTO);
    }

    @Override
    public OrderDto getOrderByIdAndIsDeletedFalse(long id) {
        logger.info("Fetching order with ID {} where isDeleted = false", id);

        return orderRepository.findByOrderIdAndIsDeletedFalse(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }
}
