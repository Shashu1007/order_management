package com.orderiFy.app.orderModule.controller;

import com.orderiFy.app.framework.global.selections.SelectOptionDto;
import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.mappers.OrderMapper;
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

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }





    @PostMapping("/")
    public OrderDto createOrder(@RequestBody OrderDto dto) {
        return orderService.createOrder(dto);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderByIdAndIsDeletedFalse(id);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderNumber") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        // Log parameters
        System.out.println("Received parameters: ");
        System.out.println("Page: " + page);
        System.out.println("Size: " + size);
        System.out.println("SortBy: " + sortBy);
        System.out.println("SortDir: " + sortDir);

        // If no keyword is provided, it will default to empty string



        Page<OrderDto> orderPage = orderService.findAllOrders( page, size, sortBy, sortDir);

        // Log the results from the service
        System.out.println("Total orders found: " + orderPage.getTotalElements());

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

        // Log parameters
        System.out.println("Received parameters: ");
        System.out.println("Keyword: " + keyword);
        System.out.println("Page: " + page);
        System.out.println("Size: " + size);
        System.out.println("SortBy: " + sortBy);
        System.out.println("SortDir: " + sortDir);

        // If no keyword is provided, it will default to empty string
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "";
        }
        else {
            keyword=keyword.trim();
        }


        Page<OrderDto> orderPage = orderService.getAllOrders(keyword, page, size, sortBy, sortDir);

        // Log the results from the service
        System.out.println("Total orders found: " + orderPage.getTotalElements());

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

    @GetMapping("/newOrder")
    public ResponseEntity<String> getNextOrderNumber(){
        String nextOrderNumber = orderService.getNextOrderNumber();
        return ResponseEntity.ok(nextOrderNumber);
    }

    @GetMapping("/getByOrderId/{id}")
        public Order getById(@PathVariable Long id){
            return orderService.getOrderById(id);

    }



}
