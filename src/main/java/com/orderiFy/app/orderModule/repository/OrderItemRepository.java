package com.orderiFy.app.orderModule.repository;

import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    // Fixing the query to correctly use JPQL syntax
    @Query("SELECT oi FROM OrderItems oi WHERE oi.isDeleted = false AND oi.orderItemId = :orderItemId")
    Optional<OrderItems> findByOrderItemIdAndIsDeletedFalse(@Param("orderItemId") Long id);

    // Fixing the query to reference order.orderId, since orderId is in the Order entity
    @Query("SELECT oi FROM OrderItems oi WHERE oi.isDeleted = false AND oi.order.orderId = :orderId")
    List<OrderItems> findAllByOrderIdAndIsDeletedFalse(@Param("orderId") Long id);

}

