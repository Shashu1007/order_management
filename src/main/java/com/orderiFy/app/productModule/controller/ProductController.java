package com.orderiFy.app.productModule.controller;

import com.orderiFy.app.customerModule.dto.CustomerDto;
import com.orderiFy.app.productModule.dto.ProductDto;
import com.orderiFy.app.productModule.entity.Product;
import com.orderiFy.app.productModule.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get paginated products
    @GetMapping("/paginated")
    public ResponseEntity<Map<String, Object>> getPaginatedProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        // If no keyword is provided, it will default to empty string
        if (keyword == null) {
            keyword = "";
        }

        Page<ProductDto> productPage = productService.getPaginatedProducts(keyword, page, size, sortBy, sortDir);

        Map<String, Object> response = new HashMap<>();
        response.put("products", productPage.getContent());
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());
        response.put("sortBy", sortBy);
        response.put("sortDir", sortDir);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();

    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    // Delete product
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
