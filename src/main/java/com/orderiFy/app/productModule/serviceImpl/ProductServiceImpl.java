package com.orderiFy.app.productModule.serviceImpl;

import com.orderiFy.app.customerModule.dto.CustomerDto;
import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.productModule.dto.ProductDto;
import com.orderiFy.app.productModule.entity.Product;
import com.orderiFy.app.productModule.mappers.ProductMapper;
import com.orderiFy.app.productModule.repository.ProductRepository;
import com.orderiFy.app.productModule.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }



    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            logger.warn("No products found in the database");
        }

        List<ProductDto> productDtos = products.stream()
                .map(productMapper::productToProductDto)
                .toList();

        // Log the productDtos to check if mapping is correct
        logger.debug("Mapped ProductDtos: {}", productDtos);

        return productDtos;
    }


    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductDto)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Product updatedProduct = productMapper.productDtoToProduct(productDto);
        updatedProduct.setProductId(existingProduct.getProductId());
        updatedProduct.setUpdatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.productToProductDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.safeDelete(id);
    }

    @Override
    public Page<ProductDto> getPaginatedProducts(String keyword, int page, int size, String sortBy, String sortDir) {
        // If keyword is null or empty, treat it as an empty string
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "";
        }

        // Default sort direction to ascending if invalid
        Sort sort = (sortDir != null && sortDir.equalsIgnoreCase("desc"))
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        // Create pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch products with pagination and filtering by keyword
        Page<Product> productPage = productRepository.findAllProducts(keyword, pageable);

        // Map entities to DTOs and return
        return productPage.map(productMapper::productToProductDto);
    }



}