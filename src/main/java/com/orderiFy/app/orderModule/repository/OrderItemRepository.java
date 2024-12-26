package com.orderiFy.app.orderModule.repository;

import com.orderiFy.app.orderModule.dto.OrderItemDto;
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

    // Fix the query to return OrderItems instead of OrderItemDto
    @Query("SELECT oi FROM OrderItems oi WHERE oi.isDeleted = false AND oi.orderItemId = :orderItemId")
    Optional<OrderItems> findByOrderItemIdAndIsDeletedFalse(@Param("orderItemId") Long id);

    // Fix the query to return OrderItems instead of OrderItemDto
    @Query("SELECT oi FROM OrderItems oi WHERE oi.isDeleted = false AND oi.order.orderId = :orderId")
    List<OrderItems> findAllByOrderIdAndIsDeletedFalse(@Param("orderId") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE OrderItems c SET c.isDeleted = true WHERE c.orderItemId = :orderItemId")
    void safeDeleteOrderItem(@Param("orderItemId") Long orderItemId);

    @Modifying
    @Transactional
    @Query("UPDATE OrderItems c SET c.isDeleted = true WHERE c.orderItemId IN :ids")
    void safeDeleteOrderItems(@Param("ids") List<Long> ids);
}


