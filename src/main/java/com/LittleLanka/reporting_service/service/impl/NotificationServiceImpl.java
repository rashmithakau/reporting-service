package com.LittleLanka.reporting_service.service.impl;

import com.LittleLanka.reporting_service.dto.NotificationDTO;
import com.LittleLanka.reporting_service.entity.Notification;
import com.LittleLanka.reporting_service.repository.NotificationRepository;
import com.LittleLanka.reporting_service.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        notification.setOutletId(dto.getOutletId());
        notification.setMessage(dto.getMessage());
        notification.setDate(LocalDateTime.now());

        Notification savedNotification = notificationRepository.save(notification);
        return new NotificationDTO(
                savedNotification.getOutletId(),
                savedNotification.getMessage(),
                savedNotification.getDate()
        );
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(notification -> new NotificationDTO(
                        notification.getOutletId(),
                        notification.getMessage(),
                        notification.getDate()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> getNotificationsByOutletId(Long outletId) {
        List<Long> outletIds = new ArrayList<>(Arrays.asList(outletId, -1L)); // Example outlet IDs
        List<Notification> notifications = notificationRepository.findTop8ByOutletIdInOrderByDateDesc(outletIds);

        return notifications;
    }
}