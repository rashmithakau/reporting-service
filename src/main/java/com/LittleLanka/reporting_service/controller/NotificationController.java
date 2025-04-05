package com.LittleLanka.reporting_service.controller;

import com.LittleLanka.reporting_service.dto.Notification;
import com.LittleLanka.reporting_service.dto.NotificationDTO;
import com.LittleLanka.reporting_service.dto.NotificationMessage;
import com.LittleLanka.reporting_service.service.NotificationService;
import com.LittleLanka.reporting_service.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;


@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<StandardResponse> saveNotification(@RequestBody NotificationDTO notificationDTO) {
        // Notify all subscribers about the new notification
        messagingTemplate.convertAndSend("/topic/notifications", notificationDTO);

        System.out.println(notificationDTO);

        // Create a standard response
        StandardResponse response = new StandardResponse();
        response.setCode(HttpStatus.CREATED.value());
        response.setMessage("Notification sent successfully");
        response.setData(notificationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllNotifications() {
        StandardResponse response=new StandardResponse();
        response.setCode(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(notificationService.getAllNotifications());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<StandardResponse> getNotificationsByUserId(@PathVariable Long userId) {
        StandardResponse response = new StandardResponse();
        response.setCode(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(notificationService.getNotificationsByUserId(userId));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // For direct messaging
    @MessageMapping("/send-notification")
    @SendTo("/topic/notifications")
    public NotificationDTO sendNotification(@Payload NotificationDTO message) throws Exception {
        notificationService.saveNotification(message);
        System.out.println(message);
        return message;
    }

    // For targeted user notifications
    public void sendPrivateNotification(String userId, Notification notification) {
        messagingTemplate.convertAndSendToUser(
                userId,
                "/queue/notifications",
                notification
        );
    }

}
