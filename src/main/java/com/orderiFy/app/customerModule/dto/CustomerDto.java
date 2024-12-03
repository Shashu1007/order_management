    package com.orderiFy.app.customerModule.dto;

    import lombok.*;

    import java.time.LocalDate;
    import lombok.Builder;
    import lombok.Data;
    import lombok.ToString;

    import java.time.LocalDate;

    @ToString
    @Data

    public class CustomerDto {
        private Long id;
        private String name;
        private String phoneNumber;
        private String email;
        private String address;
        private LocalDate dob;
        private Boolean isDeleted;
    }
