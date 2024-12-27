package com.orderiFy.app.customerModule.serviceImpl;

import com.orderiFy.app.customerModule.dto.CustomerDto;
import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.customerModule.exceptions.CustomerNotFoundException;
import com.orderiFy.app.customerModule.mappers.CustomerMapper;
import com.orderiFy.app.customerModule.repository.CustomerRepository;
import com.orderiFy.app.customerModule.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        Customer customer = customerMapper.toEntity(dto); // Use instance method
        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer); // Use instance method
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return customerRepository.findByCustomerIdAndIsDeletedFalse(id)
                .map(customerMapper::toDTO) // Use instance method
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findByIsDeletedFalse().stream()
                .map(customerMapper::toDTO) // Use instance method
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        Customer existingCustomer = customerRepository.findByCustomerIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        // Update customer details
        existingCustomer.setCustomerName(dto.getCustomerName());
        existingCustomer.setPhoneNumber(dto.getPhoneNumber());
        existingCustomer.setEmail(dto.getEmail());
        existingCustomer.setAddress(dto.getAddress());
        existingCustomer.setDob(dto.getDob());
        existingCustomer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.toDTO(updatedCustomer); // Use instance method
    }



    @Override
    public void deleteCustomers(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new RuntimeException("Product IDs cannot be empty.");
        }

        customerRepository.safeDeleteCustomers(ids);
    }

    @Override
    public void deleteCustomer(Long id) {


        customerRepository.safeDeleteCustomer(id);
    }



    @Override
    public Page<CustomerDto> getPaginatedCustomers(String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = (sortDir != null && sortDir.equalsIgnoreCase("desc"))
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Customer> customerPage = customerRepository.findAllCustomers(keyword, pageable);
        return customerPage.map(customerMapper::toDTO); // Use instance method
    }
}
