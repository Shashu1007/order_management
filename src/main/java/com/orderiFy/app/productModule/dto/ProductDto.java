package com.orderiFy.app.productModule.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String productCategory;
    private Integer stockQuantity;
    private String uom;
    private Double pricePerUnit;
    private String specification;
    private Boolean isDeleted;
    private Long createdBy;
    private Long updatedBy;
}
