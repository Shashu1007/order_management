package com.orderiFy.app.productModule.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.orderiFy.app.productModule.dto.ProductDto;

import java.util.List;


@Service
public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductById(long id);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(long id, ProductDto productDto);

    void deleteProduct(long id);

    Page<ProductDto> getPaginatedProducts(String productName, String productCategory, int page, int size, String sortBy, String sortDir) ;

}
