package com.orderiFy.app.repository;

import com.orderiFy.app.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions ,Integer> {
}
