package com.orderiFy.app.productModule.dto;

import com.orderiFy.app.framework.util.Enums;
import lombok.*;

@Data
public class ProductDto {
    private long productId;
    private String productName;
    private String productCategory;
    private Integer stockQuantity;
    private Enums.Uom uom;
    private Double pricePerUnit;
    private String specification;


}
