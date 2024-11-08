package com.orderiFy.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {

    private Long serviceId;
    private String serviceName;
    private String description;
    private Date CreatedAt;
    private Date updatedAt;
    private Long createdBy;
    private Long UpdatedBy;


}
