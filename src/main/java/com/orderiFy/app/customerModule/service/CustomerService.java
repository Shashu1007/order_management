package com.orderiFy.app.customerModule.service;

import com.orderiFy.app.customerModule.dto.CustomerDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long id);

    CustomerDto createCustomer(CustomerDto customerDTO);

    CustomerDto updateCustomer(Long id, CustomerDto customerDTO);

    void deleteCustomer(Long id);

    Page<CustomerDto> getPaginatedCustomers(String keyword, int page, int size, String sortBy, String sortDir);
}
