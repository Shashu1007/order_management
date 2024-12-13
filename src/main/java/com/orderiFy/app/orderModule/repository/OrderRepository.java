package com.orderiFy.app.orderModule.repository;

import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.orderModule.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderIdAndIsDeletedFalse(Long id);

    List<Order> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Order c SET c.isDeleted = true WHERE c.orderId = :orderId")
    void safeDeleteOrder(@Param("orderId") Long id);


    @Modifying
    @Transactional
    @Query("UPDATE Order c SET c.isDeleted = true WHERE c.orderId = :orderId")
    void safeDeleteOrders(@Param("orderId")List<Long> ids);


    @Query("SELECT o FROM Order o WHERE o.isDeleted = false AND (" +
            "LOWER(o.customerName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(o.orderTakenByUsername) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(o.orderStatus) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Order> findByKeyword(@Param("keyword") String keyword, Pageable pageable);









}
