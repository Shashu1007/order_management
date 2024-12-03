package com.orderiFy.app.productModule.mappers;

import com.orderiFy.app.enums.Enums;
import com.orderiFy.app.productModule.dto.ProductDto;
import com.orderiFy.app.productModule.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDTO(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductCategory(product.getProductCategory());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setUom(product.getUom() != null ? product.getUom().name() : null);
        dto.setPricePerUnit(product.getPricePerUnit());
        dto.setSpecification(product.getSpecification());
        dto.setIsDeleted(product.getIsDeleted());
        dto.setCreatedBy(product.getCreatedBy());
        dto.setUpdatedBy(product.getUpdatedBy());
        return dto;
    }

    public Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setProductId(dto.getProductId()); // Map DTO field to entity field
        product.setProductName(dto.getProductName());
        product.setProductCategory(dto.getProductCategory());
        product.setStockQuantity(dto.getStockQuantity());
        product.setUom(dto.getUom() != null ? Enums.Uom.valueOf(dto.getUom()) : null); // Convert String to Enum
        product.setPricePerUnit(dto.getPricePerUnit());
        product.setSpecification(dto.getSpecification());
        product.setIsDeleted(dto.getIsDeleted());
        product.setCreatedBy(dto.getCreatedBy());
        product.setUpdatedBy(dto.getUpdatedBy());
        return product;
    }
}
