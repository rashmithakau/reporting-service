package com.LittleLanka.reporting_service.repository;

import com.LittleLanka.reporting_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}
