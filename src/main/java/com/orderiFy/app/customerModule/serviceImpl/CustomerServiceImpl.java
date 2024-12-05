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
        savedCustomer.onCreate();

        return customerMapper.toDTO(savedCustomer);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return customerRepository.findByCustomerIdAndIsDeletedFalse(id)
                .map(customerMapper::toDTO)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : " + id));
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findByIsDeletedFalse().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : "+ id ));

        // Update fields
        existingCustomer.setCustomerName(dto.getName());
        existingCustomer.setPhoneNumber(dto.getPhoneNumber());
        existingCustomer.setEmail(dto.getEmail());
        existingCustomer.setAddress(dto.getAddress());
        existingCustomer.onUpdate();
        return customerMapper.toDTO(customerRepository.save(existingCustomer));
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : " + id));

        customerRepository.safeDeleteCustomer(id);

        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }



    @Override
    public Page<CustomerDto> getPaginatedCustomers(String name, String email, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Customer> customerPage = customerRepository.findByFilters(name, email, pageable);

        return customerPage.map(customerMapper::toDTO);
    }


}




