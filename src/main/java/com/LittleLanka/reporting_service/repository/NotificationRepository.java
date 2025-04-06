package com.LittleLanka.reporting_service.repository;

import com.LittleLanka.reporting_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByOutletId(Long outletId);

    @Query("SELECT n FROM Notification n WHERE n.outletId IN :outletIds ORDER BY n.date DESC")
    List<Notification> findTop8ByOutletIdInOrderByDateDesc(@Param("outletIds") List<Long> outletIds);


}
