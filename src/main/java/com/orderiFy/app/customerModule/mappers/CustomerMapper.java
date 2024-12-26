package com.orderiFy.app.customerModule.mappers;

import com.orderiFy.app.customerModule.dto.CustomerDto;
import com.orderiFy.app.customerModule.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setCustomerName(customer.getCustomerName());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setEmail(customer.getEmail());
        customerDto.setAddress(customer.getAddress());
        customerDto.setDob(customer.getDob());

        return customerDto;
    }

    public Customer toEntity(CustomerDto customerDto) {
        if (customerDto == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setEmail(customerDto.getEmail());
        customer.setAddress(customerDto.getAddress());
        customer.setDob(customerDto.getDob());

        return customer;
    }
}
