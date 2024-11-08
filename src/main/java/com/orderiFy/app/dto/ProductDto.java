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
public class ProductDto {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Date CreatedAt;
    private Date updatedAt;
    private Long CreatedBy;
    private Long UpdatedBy;


}
