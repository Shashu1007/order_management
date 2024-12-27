package com.orderiFy.app.customerModule.repository;

import com.orderiFy.app.customerModule.entity.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerIdAndIsDeletedFalse(Long id);

    List<Customer> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.isDeleted = true WHERE c.customerId in :ids")
    void safeDeleteCustomers(@Param("ids") List<Long> ids);





    @Query("SELECT c FROM Customer c WHERE c.isDeleted = false AND (" +
            "LOWER(c.customerName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(c.customerId AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(c.dob AS string) LIKE CONCAT('%', :keyword, '%'))")
    Page<Customer> findAllCustomers(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.isDeleted = true WHERE c.customerId=:id")
    void safeDeleteCustomer(@Param("id") Long id);
}







