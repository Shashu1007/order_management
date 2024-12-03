package com.orderiFy.app.customerModule.repository;

import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.customerModule.mappers.CustomerMapper;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("UPDATE Customer c SET c.isDeleted = true WHERE c.customerId = :id")
    void safeDeleteCustomer(@Param("id") Long id);




}