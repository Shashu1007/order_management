package com.orderiFy.app.productModule.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.orderiFy.app.productModule.dto.ProductDto;

import java.util.List;


@Service
public interface ProductService {




    ProductDto getProductById(Long id);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);

    void deleteProducts(List<Long> ids);

    Page<ProductDto> getPaginatedProducts(String keyword, int page, int size, String sortBy, String sortDir) ;

    List<ProductDto> findAllProducts();
}
