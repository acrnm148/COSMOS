package com.cosmos.back.dto.request;

import com.cosmos.back.model.NotificationType;
import com.cosmos.back.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private Long userSeq;
    private NotificationType notificationType;
    private String content;
    private String url;
}