    package com.orderiFy.app.customerModule.dto;


    import java.time.LocalDate;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.Data;


    @Data
    public class CustomerDto {
        private Long customerId;
        private String customerName;
        private String phoneNumber;
        private String email;
        private String address;
        @JsonFormat(pattern = "dd-MM-yyyy")
        private LocalDate dob;
    }
