package com.LittleLanka.reporting_service.service;

import com.LittleLanka.reporting_service.dto.NotificationDTO;
import com.LittleLanka.reporting_service.entity.Notification;

import java.util.List;

public interface NotificationService {
    NotificationDTO saveNotification(NotificationDTO notificationDTO);
    List<NotificationDTO> getAllNotifications();

    List<Notification> getNotificationsByOutletId(Long userId);
}
