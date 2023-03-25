package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String content; //내용

    @Column(nullable = false)
    private Boolean isRead; //읽음여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType; //알림 타입

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver; //알림받는 사람

    private String url;

    public void read() {
        isRead = true;
    }

    @Builder
    public Notification(User receiver, NotificationType notificationType, String content, String url, Boolean isRead) throws Exception {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = content;
        this.url = url;
        this.isRead = isRead;
    }
}
