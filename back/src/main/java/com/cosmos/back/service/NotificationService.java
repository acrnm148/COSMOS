package com.cosmos.back.service;

import com.cosmos.back.dto.response.NotificationDto;
import com.cosmos.back.model.Notification;
import com.cosmos.back.model.User;
import com.cosmos.back.repository.noti.EmitterRepository;
import com.cosmos.back.repository.noti.NotificationRepository;
import com.cosmos.back.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    @Value("${spring.sse.time}")
    private Long timeout;
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;
    private final UserRepository userRepository;

    /**
     * 알림 구독
     */
    public SseEmitter subscribe(Long userSeq, String lastEventId) {
        String emitterId = makeTimeIncludeId(userSeq);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(-1L));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(userSeq);
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .id(eventId)
                    .data("sse [userSeq=" + userSeq + "]"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("SSE connected:" + emitter);
        return emitter;
    }

    /**
     * 알림 전송
     */
    public void send(String event, Long userSeq, String content, Boolean isClicked) {
        User receiver = userRepository.findByUserSeq(userSeq);
        if (receiver != null) {
            Notification notification = createNotification(event, receiver, content);

            // 알림 db에 저장 - SSE는 새로고침하면 이전 알림을 볼 수 없어서 추가
            notificationRepository.save(notification);

            String receiverId = String.valueOf(userSeq);
            String eventId = receiverId + "_" + System.currentTimeMillis();

            // 로그인 한 유저의 SseEmitter 모두 가져오기
            Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserSeq(receiverId);
            sseEmitters.forEach(
                    (key, emitter) -> {
                        // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                        emitterRepository.saveEventCache(key, notification);
                        // 데이터 전송
                        sendNotification(event, emitter, eventId, key, notification);
                    }
            );
        }
    }

    /**
     * 유저별 알림 조회
     */
    @Transactional
    public List<NotificationDto> findAllNotifications(Long userSeq) {
        List<Notification> notifications = notificationRepository.findAllByUserSeq(userSeq);
        notifications.stream()
                .forEach(notification -> notification.read());
        return notifications.stream()
                .map(NotificationDto::create)
                .collect(Collectors.toList());
    }

    /**
     * 읽지 않은 알림 개수
     */
    public Long countUnReadNotifications(Long memberId) {
        return notificationRepository.countUnReadNotifications(memberId);
    }

    /**
     * 읽은 알림 개수
     */
    public Long countReadNotifications(Long memberId) {
        return notificationRepository.countReadNotifications(memberId);
    }

    /**
     * 알림 개별 삭제
     */
    public void deleteNoti(Long notiId) {
        notificationRepository.deleteById(notiId);
    }

    /**
     * 알림 전체 삭제
     */
    public void deleteAllNotis(Long userSeq) {
        notificationRepository.deleteByReceiver(userSeq);
    }

    /**
     * 알림 ID 생성 (생성시간 포함)
     */
    private String makeTimeIncludeId(Long userSeq) {
        return userSeq + "_" + System.currentTimeMillis();
    }

    /**
     * 알림 전송
     */
    private void sendNotification(String event, SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .name(event)
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            //throw new RuntimeException("sse connect error!");
        }
    }

    private Notification createNotification(String event, User receiver, String content) {
        try {
            return Notification.builder()
                    .event(event)
                    .receiver(receiver)
                    .isRead(false)
                    .content(content)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
