package com.cosmos.back.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Embedded
    private NotificationContent content; //내용

    @Embedded
    private RelatedURL url; //관련url

    @Column(nullable = false)
    private Boolean isRead; //읽음여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType; //알림 타입

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver; //알림받는 사람

    @Builder
    public Notification(User receiver, NotificationType notificationType, String content, String url, Boolean isRead) throws Exception {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = new NotificationContent(content);
        this.url = new RelatedURL(url);
        this.isRead = isRead;
    }

    public String getContent() {
        return content.getContent();
    }

    public String getUrl() {
        return url.getUrl();
    }
}
