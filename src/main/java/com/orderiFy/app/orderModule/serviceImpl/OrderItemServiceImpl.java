package com.orderiFy.app.orderModule.serviceImpl;

import com.orderiFy.app.customerModule.exceptions.CustomerNotFoundException;
import com.orderiFy.app.exception.ResourceNotFoundException;
import com.orderiFy.app.orderModule.dto.OrderItemDto;
import com.orderiFy.app.orderModule.entity.OrderItems;
import com.orderiFy.app.orderModule.mappers.OrderItemsMapper;
import com.orderiFy.app.orderModule.repository.OrderItemRepository;
import com.orderiFy.app.orderModule.service.OrderItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemsMapper orderItemsMapper;
    private final HttpSession session;




    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemsMapper orderItemsMapper, HttpSession session) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemsMapper = orderItemsMapper;
        this.session = session;
    }




    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderIdAndIsDeletedFalse(orderId)
                .stream()
                .map(orderItemsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto GetOrderItemById(Long orderItemId){
        return orderItemRepository.findByOrderItemIdAndIsDeletedFalse(orderItemId)
                .map(orderItemsMapper::toDTO)
                .orElseThrow(()->new ResourceNotFoundException("Order Item Not Found With The Id " + orderItemId ));

    }


    @Override
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        OrderItems orderItems = orderItemsMapper.toEntity(orderItemDto);
        orderItems.setCreatedAt(LocalDateTime.now());
        orderItems.setCreatedBy(session.getAttribute("username").toString());
        orderItems = orderItemRepository.save(orderItems);

        return orderItemsMapper.toDTO(orderItems);
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemdto) {
        OrderItems existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with order id : "+ id));
        OrderItems updatedOrderItem = orderItemsMapper.toEntity(orderItemdto);
        updatedOrderItem.setUpdatedAt(LocalDateTime.now());
        updatedOrderItem.setUpdatedBy(session.getAttribute("username").toString());

        return orderItemsMapper.toDTO(orderItemRepository.save(updatedOrderItem));
    }

    @Override
    public void deleteOrderItems(List<Long> ids) {
        // Retrieve the list of orders by the given IDs
        List<OrderItems> orderItems = orderItemRepository.findAllById(ids);

        if (orderItems.isEmpty()) {
            throw new CustomerNotFoundException("No orders found for the provided ids: " + ids);
        }

        // Soft delete the orders by calling the safeDeleteOrders method
        orderItemRepository.safeDeleteOrderItems(ids);  // Assuming safeDeleteOrders performs soft delete logic

        // Iterate over the list of orders to update the 'updatedAt' timestamp
        for (OrderItems orderItem : orderItems) {
            orderItem.setUpdatedAt(LocalDateTime.now());
            orderItemRepository.save(orderItem);  // Save the updated timestamp (if required by your logic)
        }
    }


    @Override
    public void deleteOrderItem(Long id) {
        OrderItems orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : " + id));

        orderItemRepository.safeDeleteOrderItem(id); // Soft delete the order
        orderItem.setUpdatedAt(LocalDateTime.now());
        orderItemRepository.save(orderItem);

    }
}
