package com.orderiFy.app.productModule.dto;

import lombok.*;

@Data
public class ProductDto {
    private long productId;
    private String productName;
    private String productCategory;
    private Integer stockQuantity;
    private String uom;
    private Double pricePerUnit;
    private String specification;
    private Boolean isDeleted;
    private long createdBy;
    private long updatedBy;


}
