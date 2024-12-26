package com.orderiFy.app.productModule.mappers;

import com.orderiFy.app.productModule.dto.ProductDto;
import com.orderiFy.app.productModule.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Convert DTO to Entity
    public Product toEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setProductCategory(dto.getProductCategory());
        product.setStockQuantity(dto.getStockQuantity());
        product.setUom(dto.getUom());
        product.setPricePerUnit(dto.getPricePerUnit());
        product.setSpecification(dto.getSpecification());
        return product;
    }

    // Convert Entity to DTO
    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductCategory(product.getProductCategory());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setUom(product.getUom());
        dto.setPricePerUnit(product.getPricePerUnit());
        dto.setSpecification(product.getSpecification());
        return dto;
    }
}
