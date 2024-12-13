package com.orderiFy.app.productModule.mappers;

import com.orderiFy.app.customerModule.mappers.CustomerMapper;
import com.orderiFy.app.framework.util.Enums;
import com.orderiFy.app.productModule.dto.ProductDto;
import com.orderiFy.app.productModule.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface ProductMapper {


    ProductDto productToProductDto(Product product);
    Product productDtoToProduct(ProductDto productDto);
}

