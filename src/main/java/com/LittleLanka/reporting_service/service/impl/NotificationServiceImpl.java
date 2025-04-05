package com.LittleLanka.reporting_service.service.impl;

import com.LittleLanka.reporting_service.dto.NotificationDTO;
import com.LittleLanka.reporting_service.entity.Notification;
import com.LittleLanka.reporting_service.repository.NotificationRepository;
import com.LittleLanka.reporting_service.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationDTO saveNotification(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setUserId(dto.getUserId());
        notification.setMessage(dto.getMessage());
        notification.setDate(dto.getDate());
        Notification savedNotification = notificationRepository.save(notification);
        return new NotificationDTO(
                savedNotification.getUserId(),
                savedNotification.getMessage(),
                savedNotification.getDate()
        );
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(notification -> new NotificationDTO(
                        notification.getUserId(),
                        notification.getMessage(),
                        notification.getDate()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}