package com.LittleLanka.reporting_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationDTO {
    private Long outletId;
    private String message;
    private LocalDateTime date;
}

