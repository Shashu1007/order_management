package com.orderiFy.app.orderModule.controller;

import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.customerModule.service.CustomerService;
import com.orderiFy.app.framework.global.selections.SelectOptionDto;
import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.service.OrderService;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService,CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;


    }




    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        // Create the order and order items dynamically in the service
        OrderDto savedOrder = orderService.createOrder(orderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }



    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderByIdAndIsDeletedFalse(id);
    }


    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderNumber") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<OrderDto> orderPage = orderService.findAllOrders( keyword,page, size, sortBy, sortDir);

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orderPage.getContent());
        response.put("currentPage", orderPage.getNumber());
        response.put("totalItems", orderPage.getTotalElements());
        response.put("totalPages", orderPage.getTotalPages());
        response.put("sortBy", sortBy);
        response.put("sortDir", sortDir);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @GetMapping("/paginated")
    public ResponseEntity<Map<String, Object>> getPaginatedProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderNumber") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "";
        } else {
            keyword = keyword.trim();
        }
        Page<OrderDto> orderPage = orderService.getAllOrders(keyword, page, size, sortBy, sortDir);

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orderPage.getContent());
        response.put("currentPage", orderPage.getNumber());
        response.put("totalItems", orderPage.getTotalElements());
        response.put("totalPages", orderPage.getTotalPages());
        response.put("sortBy", sortBy);
        response.put("sortDir", sortDir);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable Long id, @RequestBody OrderDto dto) {
        return orderService.updateOrder(id, dto);
    }




    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }


    @DeleteMapping("/")
    public void deleteOrders(@RequestBody List<Long> ids) {
        orderService.deleteOrders(ids);
    }



    @GetMapping("/getByOrderId/{id}")
        public Order getById(@PathVariable Long id){
            return orderService.getOrderById(id);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Log the error
        ex.printStackTrace();
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
