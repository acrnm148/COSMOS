package com.cosmos.back.service;

import com.cosmos.back.annotation.Generated;
import com.cosmos.back.dto.response.NotificationDto;
import com.cosmos.back.model.Notification;
import com.cosmos.back.model.NotificationType;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    @Value("${spring.sse.time}")
    private Long timeout;
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;
    private final UserRepository userRepository;
    private static final AtomicLong counter = new AtomicLong();

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
        //sendNotification(emitter, eventId, emitterId, "sse [userSeq=" + userSeq + "]");

        /*
        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (lastEventId!=null | !lastEventId.isEmpty()) {
            Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserSeq(String.valueOf(userSeq));
            eventCaches.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
        }*/

        System.out.println("SSE connected:" + emitter);
        return emitter;
    }

    /**
     * 알림 전송
     */
    public void send(Long userSeq, NotificationType notificationType, String content, String url) {
        User receiver = userRepository.findByUserSeq(userSeq);
        if (receiver != null) {
            Notification notification = createNotification(receiver, notificationType, content, url);
            System.out.println("notification!?!?!?!? = " + notification);
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
                        sendNotification(emitter, eventId, key, notification);
                    }
            );
        }
        //System.out.println("개수:"+counter.incrementAndGet());
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
    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .name("sse")
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            //throw new RuntimeException("sse connect error!");
        }
    }

    private Notification createNotification(User receiver, NotificationType notificationType, String content, String url) {
        try {
            return Notification.builder()
                    .receiver(receiver)
                    .notificationType(notificationType)
                    .content(content)
                    .url(url)
                    .isRead(false)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
