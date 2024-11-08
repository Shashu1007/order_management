package com.orderiFy.app.repository;

import com.orderiFy.app.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment , Integer> {
}
