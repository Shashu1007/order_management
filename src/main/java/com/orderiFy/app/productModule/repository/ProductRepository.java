package com.orderiFy.app.productModule.repository;

import com.orderiFy.app.productModule.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND (" +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.productCategory) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.uom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +  // Search by uom as well
            "CAST(p.productId AS STRING) LIKE CONCAT('%', :keyword, '%'))")  // Search by productId as well
    Page<Product> findAllProducts(@Param("keyword") String keyword, Pageable pageable);



    @Query("SELECT p FROM Product p where p.isDeleted = False ")
    List<Product> findAllProducts();


    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.isDeleted = true WHERE p.id IN :ids")
    void safeDeleteProducts(@Param("ids") List<Long> ids);
}
