    package com.orderiFy.app.customerModule.controller;

    import com.orderiFy.app.customerModule.dto.CustomerDto;
    import com.orderiFy.app.customerModule.entity.Customer;
    import com.orderiFy.app.customerModule.exceptions.CustomerNotFoundException;
    import com.orderiFy.app.customerModule.repository.CustomerRepository;
    import com.orderiFy.app.customerModule.service.CustomerService;
    import org.springframework.data.domain.Page;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Optional;


    @RestController
    @RequestMapping("/api/customers")
    public class CustomerController {

        private final CustomerService customerService;

        public CustomerController(CustomerService customerService) {
            this.customerService = customerService;
        }

        @GetMapping("/paginated")
        public ResponseEntity<Map<String, Object>> getPaginatedCustomers(
                @RequestParam(required = false) String keyword,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "customerName") String sortBy,
                @RequestParam(defaultValue = "asc") String sortDir) {

            if (keyword == null) {
                keyword = "";
            }

            Page<CustomerDto> customerPage = customerService.getPaginatedCustomers(keyword, page, size, sortBy, sortDir);

            Map<String, Object> response = new HashMap<>();
            response.put("customers", customerPage.getContent());
            response.put("currentPage", customerPage.getNumber());
            response.put("totalItems", customerPage.getTotalElements());
            response.put("totalPages", customerPage.getTotalPages());
            response.put("sortBy", sortBy);
            response.put("sortDir", sortDir);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/all")
        public List<CustomerDto> getAllCustomers() {
            return customerService.getAllCustomers();
        }









        @GetMapping("/{id}")
        public ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable Long id) {
            CustomerDto customer = customerService.getCustomerById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("customer", customer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<CustomerDto> createCustomer( @RequestBody CustomerDto customerDto) {
            CustomerDto savedCustomer = customerService.createCustomer(customerDto);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id,  @RequestBody CustomerDto customerDto) {
            CustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @DeleteMapping("/")
        public ResponseEntity<Void> deleteCustomers(@RequestBody List<Long> ids) {
            customerService.deleteCustomers(ids);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @ExceptionHandler(CustomerNotFoundException.class)
        public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
