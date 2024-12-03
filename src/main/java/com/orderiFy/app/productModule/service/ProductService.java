package com.orderiFy.app.productModule.service;

import org.springframework.stereotype.Service;
import com.orderiFy.app.productModule.dto.ProductDto;

import java.util.List;


@Service
public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);

}
