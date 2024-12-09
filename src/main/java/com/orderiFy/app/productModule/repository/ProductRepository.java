package com.orderiFy.app.productModule.repository;

import com.orderiFy.app.productModule.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT c FROM Product c WHERE c.isDeleted = false AND " +
            "(LOWER(c.productName) LIKE LOWER(CONCAT('%', :productName, '%')) OR :productName IS NULL) AND " +
            "(LOWER(c.productCategory) LIKE LOWER(CONCAT('%', :productCategory, '%')) OR :productCategory IS NULL)")
    Page<Product> findByFilters(@Param("productName") String productName,
                                @Param("productCategory") String productCategory,
                                Pageable pageable);

}
