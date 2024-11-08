package com.orderiFy.app.repository;

import com.orderiFy.app.model.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentsRepository extends JpaRepository<Attachments, Integer> {
}
