package com.orderiFy.app.orderModule.serviceImpl;

import com.orderiFy.app.customerModule.exceptions.CustomerNotFoundException;
import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.mappers.OrderMapper;
import com.orderiFy.app.orderModule.repository.OrderRepository;
import com.orderiFy.app.orderModule.service.OrderService;
import com.orderiFy.app.productModule.dto.ProductDto;
import com.orderiFy.app.productModule.entity.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        order.setCreatedAt(LocalDateTime.now());
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
    public Page<OrderDto> getAllOrders(String keyword, int page, int size, String sortBy, String sortDir) {
        // If no keyword is provided, pass null to the repository method to fetch all products
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "";
        }


        // Correct sorting logic
        Sort sort = (sortDir != null && sortDir.equalsIgnoreCase("desc"))
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        // Create pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // Log the query parameters
        System.out.println("Fetching orders with keyword: " + keyword);

        // Use the repository to fetch paginated data
        Page<Order> orderPage = orderRepository.findByKeyword(keyword, pageable);

        // Log the number of results
        System.out.println("orders fetched: " + orderPage.getTotalElements());

        // Map the products to DTOs
        return orderPage.map(orderMapper::toDTO);
    }









    @Override
    public OrderDto updateOrder(Long id, OrderDto dto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with order id : "+ id));
        Order updatedOrder = orderMapper.toEntity(dto);
        updatedOrder.setOrderId(existingOrder.getOrderId());
        updatedOrder.setUpdatedAt(LocalDateTime.now());

        return orderMapper.toDTO(orderRepository.save(updatedOrder));
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : " + id));

        orderRepository.safeDeleteOrder(id); // Soft delete the order
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

    }


    @Override
    public void deleteOrders(List<Long> ids) {
        // Retrieve the list of orders by the given IDs
        List<Order> orders = orderRepository.findAllById(ids);

        if (orders.isEmpty()) {
            throw new CustomerNotFoundException("No orders found for the provided ids: " + ids);
        }

        // Soft delete the orders by calling the safeDeleteOrders method
        orderRepository.safeDeleteOrders(ids);  // Assuming safeDeleteOrders performs soft delete logic

        // Iterate over the list of orders to update the 'updatedAt' timestamp
        for (Order order : orders) {
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);  // Save the updated timestamp (if required by your logic)
        }
    }





    @Override
    public OrderDto getOrderByIdAndIsDeletedFalse(long id) {

        return orderRepository.findByOrderIdAndIsDeletedFalse(id)
                .map(orderMapper::toDTO)
                .orElseThrow(()-> new RuntimeException("Order Not Found With order Id " + id));
    }

}

