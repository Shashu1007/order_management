package com.orderiFy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long customerId;
    private String name;
    private String contactInfo;
    private String address;
    private Date createdAt;
    private Date updatedAt;
    private Long CreatedBy;
    private Long UpdatedBy;
}



