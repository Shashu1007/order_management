    package com.orderiFy.app.customerModule.dto;


    import java.time.LocalDate;
    import lombok.Data;


    @Data
    public class CustomerDto {
        private Long customerId;
        private String customerName;
        private String phoneNumber;
        private String email;
        private String address;
        private LocalDate dob;
    }
