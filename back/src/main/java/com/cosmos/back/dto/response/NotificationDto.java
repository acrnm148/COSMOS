package com.cosmos.back.dto.response;

import com.cosmos.back.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private String event;
    private Long id;
    private String content;

    public static NotificationDto create(Notification notification) {
        return new NotificationDto(notification.getEvent(), notification.getId(), notification.getContent());
    }
}