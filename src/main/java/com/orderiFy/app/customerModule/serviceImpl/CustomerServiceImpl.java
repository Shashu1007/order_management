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
        Customer customer = customerMapper.toEntity(dto);

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toDTO(savedCustomer);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return customerRepository.findByCustomerIdAndIsDeletedFalse(id)
                .map(customerMapper::toDTO)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : " + id));
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findByIsDeletedFalse().stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        try {
            Customer existingCustomer = customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : "+ id ));

            // update customer details
            existingCustomer.setCustomerName(dto.getCustomerName());
            existingCustomer.setPhoneNumber(dto.getPhoneNumber());
            existingCustomer.setEmail(dto.getEmail());
            existingCustomer.setAddress(dto.getAddress());

            return customerMapper.toDTO(customerRepository.save(existingCustomer));
        } catch (Exception e) {
            throw new RuntimeException("Error updating customer", e);
        }
    }


    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : " + id));

        customerRepository.safeDeleteCustomer(id); // Soft delete the customer
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer); // Save the updated entity
    }




    @Override
    public Page<CustomerDto> getPaginatedCustomers(String keyword, int page, int size, String sortBy, String sortDir) {
        // If keyword is null or empty, pass null to the repository method
        if (keyword != null || keyword.trim().isEmpty()) {
            keyword = "";
        }

        Sort sort = (sortDir != null && sortDir.equalsIgnoreCase("desc"))
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        // Create pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Customer> customerPage = customerRepository.findAllCustomers(keyword, pageable);

        return customerPage.map(customerMapper::toDTO);
    }


}



