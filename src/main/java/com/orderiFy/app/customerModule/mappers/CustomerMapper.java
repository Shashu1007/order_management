package com.orderiFy.app.customerModule.mappers;


import com.orderiFy.app.customerModule.dto.CustomerDto;
import com.orderiFy.app.customerModule.entity.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerMapper {
    public CustomerDto toDTO(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getCustomerId());
        dto.setName(customer.getCustomerName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setDob(customer.getDob());
        dto.setIsDeleted(customer.isDeleted());
        return dto;
    }

    public Customer toEntity(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setCustomerId(dto.getId());
        customer.setCustomerName(dto.getName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setDob(dto.getDob());
        customer.setDeleted(dto.getIsDeleted());
        return customer;
    }
}