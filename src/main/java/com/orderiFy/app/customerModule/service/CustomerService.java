package com.orderiFy.app.customerModule.service;

import com.orderiFy.app.customerModule.dto.CustomerDto;
import com.orderiFy.app.customerModule.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long id);

    CustomerDto createCustomer(CustomerDto customerDTO);

    CustomerDto updateCustomer(Long id, CustomerDto customerDTO);


    void deleteCustomer(Long id);



    void deleteCustomers(List<Long> ids );


    Page<CustomerDto> getPaginatedCustomers(String keyword, int page, int size, String sortBy, String sortDir);
}
