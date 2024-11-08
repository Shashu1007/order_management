package com.orderiFy.app.repository;

import com.orderiFy.app.model.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicesRepository extends JpaRepository<Invoices , Integer> {
}
