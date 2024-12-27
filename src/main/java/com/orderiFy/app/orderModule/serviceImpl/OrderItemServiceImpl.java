package com.orderiFy.app.orderModule.serviceImpl;

import com.orderiFy.app.exception.ResourceNotFoundException;
import com.orderiFy.app.orderModule.dto.OrderItemDto;
import com.orderiFy.app.orderModule.entity.OrderItems;
import com.orderiFy.app.productModule.dto.ProductDto;
import com.orderiFy.app.productModule.entity.Product;
import com.orderiFy.app.productModule.repository.ProductRepository;
import com.orderiFy.app.orderModule.mappers.OrderItemsMapper;
import com.orderiFy.app.orderModule.repository.OrderItemRepository;
import com.orderiFy.app.orderModule.repository.OrderRepository;
import com.orderiFy.app.orderModule.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemsMapper orderItemsMapper;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
                                OrderItemsMapper orderItemsMapper,
                                ProductRepository productRepository,
                                OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemsMapper = orderItemsMapper;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        List<OrderItems> orderItemsList = orderItemRepository.findAllByOrderIdAndIsDeletedFalse(orderId);
        return orderItemsList.stream()
                .map(orderItemsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderItemId) {
        OrderItems orderItems = orderItemRepository.findByOrderItemIdAndIsDeletedFalse(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found with item ID: " + orderItemId));
        return orderItemsMapper.toDTO(orderItems);
    }

    @Override
    @Transactional
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        // Check if the Product exists or create a new Product
        Product product = null;
        if (orderItemDto.getProductId() != null) {
            product = productRepository.findById(orderItemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + orderItemDto.getProductId()));
        } else if (orderItemDto.getProduct() != null) {
            // Create the Product using ProductDto
            ProductDto productDto = orderItemDto.getProduct();
            product = new Product();
            product.setProductName(productDto.getProductName());
            product.setProductCategory(productDto.getProductCategory());
            product.setStockQuantity(productDto.getStockQuantity());
            product.setUom(productDto.getUom());
            product.setPricePerUnit(productDto.getPricePerUnit());
            product.setSpecification(productDto.getSpecification());
            product = productRepository.save(product);  // Save the new product
        }

        // Create the OrderItems entity and associate it with the Order and Product
        OrderItems orderItems = orderItemsMapper.toEntity(orderItemDto);
        orderItems.setProduct(product);
        orderItems.setIsDeleted(false);

        // Save the OrderItems entity
        OrderItems savedOrderItem = orderItemRepository.save(orderItems);
        return orderItemsMapper.toDTO(savedOrderItem);
    }


    @Override
    @Transactional
    public List<OrderItemDto> createOrderItems(List<OrderItemDto> orderItemDtos) {
        List<OrderItems> orderItemsList = orderItemDtos.stream()
                .map(orderItemDto -> {
                    // Handle each order item creation (similar to createOrderItem)
                    Product product = null;
                    if (orderItemDto.getProductId() != null) {
                        product = productRepository.findById(orderItemDto.getProductId())
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + orderItemDto.getProductId()));
                    } else if (orderItemDto.getProduct() != null) {
                        // Create the Product using ProductDto
                        ProductDto productDto = orderItemDto.getProduct();
                        product = new Product();
                        product.setProductName(productDto.getProductName());
                        product.setProductCategory(productDto.getProductCategory());
                        product.setStockQuantity(productDto.getStockQuantity());
                        product.setUom(productDto.getUom());
                        product.setPricePerUnit(productDto.getPricePerUnit());
                        product.setSpecification(productDto.getSpecification());
                        product = productRepository.save(product);
                    }

                    // Create the OrderItems entity
                    OrderItems orderItems = orderItemsMapper.toEntity(orderItemDto);
                    orderItems.setProduct(product);
                    orderItems.setIsDeleted(false);

                    return orderItems;
                })
                .collect(Collectors.toList());

        // Save all OrderItems entities
        List<OrderItems> savedOrderItems = orderItemRepository.saveAll(orderItemsList);
        return savedOrderItems.stream()
                .map(orderItemsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderItemDto updateOrderItem(Long orderItemId, OrderItemDto orderItemDto) {
        OrderItems orderItems = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found with item ID: " + orderItemId));

        // Update OrderItem and Product if necessary
        if (orderItemDto.getProductId() != null) {
            Product product = productRepository.findById(orderItemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + orderItemDto.getProductId()));
            orderItems.setProduct(product);
        }

        // Map DTO to Entity and update the entity
        orderItems = orderItemsMapper.updateEntityFromDTO(orderItemDto, orderItems);

        // Save the updated OrderItem entity
        orderItemRepository.save(orderItems);

        // Return the updated OrderItem as a DTO
        return orderItemsMapper.toDTO(orderItems);
    }


    @Override
    public void deleteOrderItems(List<Long> ids) {
        orderItemRepository.safeDeleteOrderItems(ids);
    }

    @Transactional
    @Override
    public List<OrderItemDto> updateOrderItems(List<Long> orderItemIds, List<OrderItemDto> orderItemDtos) {
        // Ensure the lists have the same size, otherwise handle it as an error
        if (orderItemIds.size() != orderItemDtos.size()) {
            throw new IllegalArgumentException("The number of order item IDs does not match the number of DTOs provided.");
        }

        // Create a list to hold the updated DTOs
        List<OrderItemDto> updatedOrderItems = new ArrayList<>();

        // Iterate through each order item ID and corresponding DTO
        for (int i = 0; i < orderItemIds.size(); i++) {
            Long orderItemId = orderItemIds.get(i);
            OrderItemDto orderItemDto = orderItemDtos.get(i);

            // Find the existing OrderItem by its ID
            OrderItems orderItems = orderItemRepository.findById(orderItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order Item not found with item ID: " + orderItemId));

            // Update OrderItem and Product if necessary
            if (orderItemDto.getProductId() != null) {
                Product product = productRepository.findById(orderItemDto.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + orderItemDto.getProductId()));
                orderItems.setProduct(product);
            }

            // Map DTO to Entity and update the entity
            orderItems = orderItemsMapper.updateEntityFromDTO(orderItemDto, orderItems);

            // Save the updated OrderItem entity
            orderItemRepository.save(orderItems);

            // Convert the updated OrderItem entity to DTO and add it to the list
            OrderItemDto updatedOrderItemDto = orderItemsMapper.toDTO(orderItems);
            updatedOrderItems.add(updatedOrderItemDto);
        }

        // Return the list of updated OrderItem DTOs
        return updatedOrderItems;
    }

    @Override
    public void removeOrderItem(Long orderItemId) {
        OrderItems item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.setOrder(null);  // Disconnect item from order
        orderItemRepository.delete(item);  // Delete item from database
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.safeDeleteOrderItem(orderItemId);
    }
}
