package com.cosmos.back.repository;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.config.TestConfig;
import com.cosmos.back.model.Notification;
import com.cosmos.back.model.NotificationType;
import com.cosmos.back.model.User;
import com.cosmos.back.repository.noti.EmitterRepository;
import com.cosmos.back.repository.noti.EmitterRepositoryImpl;
import com.cosmos.back.repository.noti.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({TestConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableMockMvc
public class SseRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @SpyBean
    private EmitterRepository emitterRepository;

    @Test
    @DisplayName("emiter 저장")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void saveTest() throws Exception {
        //given
        Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
        String emitterId = "emitterId";
        SseEmitter sseEmitter = new SseEmitter();


        //when
        SseEmitter result = emitterRepository.save(emitterId, sseEmitter);

        //then
        assertEquals(result, sseEmitter);

    }

    @Test
    @DisplayName("유저가 받은 알림 리스트")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findAllByUserSeqTest() throws Exception {
        //given
        User user = User.builder().userSeq(1L).build();
        for(int i = 0; i < 5; i++) {
            Notification notification = Notification.builder().notificationType(NotificationType.ACCEPT).isRead(false).receiver(user).build();
            notificationRepository.save(notification);
        }

        //when
        List<Notification> result = notificationRepository.findAllByUserSeq(1L);

        //then
        assertThat(result.size()).isEqualTo(5);

    }

    @Test
    @DisplayName("안읽은 알림 개수")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void countUnReadNotificationsTest() throws Exception {
        //given
        User user = User.builder().userSeq(1L).build();
        for(int i = 0; i < 5; i++) {
            Notification notification = Notification.builder().notificationType(NotificationType.ACCEPT).isRead(false).receiver(user).build();
            notificationRepository.save(notification);
        }

        //when
        Long result = notificationRepository.countUnReadNotifications(1L);

        //then
        assertThat(result).isEqualTo(5);

    }

}
