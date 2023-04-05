package com.cosmos.back.model;

import com.cosmos.back.annotation.Generated;
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
@Generated
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String event; //내용

    @Column(nullable = false)
    private Boolean isRead; //읽음여부

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver; //알림받는 사람

    private String content;

    public void read() {
        isRead = true;
    }

    @Builder
    public Notification(String event, User receiver, Boolean isRead, String content) throws Exception {
        this.receiver = receiver;
        this.event = event;
        this.isRead = isRead;
        this.content= content;
    }
}
