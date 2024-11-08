package com.orderiFy.app.repository;

import com.orderiFy.app.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products , Integer> {
}
