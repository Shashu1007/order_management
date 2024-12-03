package com.orderiFy.app.customerModule.service;

import com.orderiFy.app.customerModule.dto.CustomerDto;
import com.orderiFy.app.customerModule.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface CustomerService {



    List<CustomerDto> getAllCustomers();


    CustomerDto getCustomerById(Long id);


    CustomerDto createCustomer(CustomerDto customerDTO);


    CustomerDto updateCustomer(Long id, CustomerDto customerDTO);


    void deleteCustomer(Long id);




}
