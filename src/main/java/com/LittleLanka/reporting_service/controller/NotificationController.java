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
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;


@RestController
@RequestMapping("api/v1/notifications")
@AllArgsConstructor
@CrossOrigin
public class NotificationController {

    private final NotificationService notificationService;

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping()
    public ResponseEntity<StandardResponse> saveNotification(@RequestBody NotificationDTO notificationDTO,@RequestParam Boolean isNotify) {
        if(isNotify) {
            // Notify all subscribers about the new notification
            messagingTemplate.convertAndSend("/topic/notifications", notificationDTO);
        }


        System.out.println(notificationDTO);
        notificationService.saveNotification(notificationDTO);

        // Create a standard response
        StandardResponse response = new StandardResponse();
        response.setCode(HttpStatus.CREATED.value());
        response.setMessage("Notification sent successfully");
        response.setData(notificationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<StandardResponse> getAllNotifications(@RequestParam Integer outletId) {
        StandardResponse response=new StandardResponse();
        response.setCode(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(notificationService.getAllNotifications());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{outletId}")
    public ResponseEntity<StandardResponse> getNotificationsByUserId(@PathVariable Long outletId) {
        StandardResponse response = new StandardResponse();
        response.setCode(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(notificationService.getNotificationsByOutletId(outletId));
        System.out.println("Recived reqest");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // For direct messaging
    @MessageMapping("/send-notification")
    @SendTo("/topic/notifications")
    public Notification sendNotification(NotificationMessage message) throws Exception {
        Thread.sleep(1000); // Simulated delay
        return new Notification("Hiii");
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
