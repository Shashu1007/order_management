package com.orderiFy.app.repository;

import com.orderiFy.app.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsRepository extends JpaRepository<Notifications , Integer> {
}
