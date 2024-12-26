package com.orderiFy.app.productModule.serviceImpl;

import com.orderiFy.app.productModule.dto.ProductDto;
import com.orderiFy.app.productModule.entity.Product;
import com.orderiFy.app.productModule.mappers.ProductMapper;
import com.orderiFy.app.productModule.repository.ProductRepository;
import com.orderiFy.app.productModule.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        product.setIsDeleted(false); // Ensure the product is not deleted when created
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existingProduct.setProductName(productDto.getProductName());
        existingProduct.setProductCategory(productDto.getProductCategory());
        existingProduct.setStockQuantity(productDto.getStockQuantity());
        existingProduct.setUom(productDto.getUom());
        existingProduct.setPricePerUnit(productDto.getPricePerUnit());
        existingProduct.setSpecification(productDto.getSpecification());

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setIsDeleted(true);
        productRepository.save(product);
    }

    @Override
    public Page<ProductDto> getPaginatedProducts(String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAllProducts(keyword, pageable);
        return productPage.map(productMapper::toDto);
    }
}
