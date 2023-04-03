package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.response.NotificationDto;
import com.cosmos.back.model.Notification;
import com.cosmos.back.repository.noti.EmitterRepository;
import com.cosmos.back.repository.noti.NotificationRepository;
import com.cosmos.back.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private EmitterRepository emitterRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("subscribe 알림 구독")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void subscribeTest() throws Exception{
        //given
        String emitterId = "emitterId";
        SseEmitter emitter = new SseEmitter();

        when(emitterRepository.save(emitterId, emitter)).thenReturn(emitter);
        doNothing().when(emitterRepository).deleteById(emitterId);

        //when
        SseEmitter result = notificationService.subscribe(1L, "lastEventId");

        //then
        System.out.println("result = " + result);

    }

    @Test
    @DisplayName("유저별 알림 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findAllNotificationsTest() throws Exception {
        //given
        List<Notification> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Notification notification = Notification.builder().build();
            list.add(notification);
        }

        when(notificationRepository.findAllByUserSeq(1L)).thenReturn(list);

        //when
        List<NotificationDto> result = notificationService.findAllNotifications(1L);

        //then
        assertThat(result.size()).isEqualTo(list.size());

    }

    @Test
    @DisplayName("읽지 않은 알림 개수")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void countUnReadNotificationsTest() throws Exception{
        //given
        Long count = 5L;
        when(notificationRepository.countUnReadNotifications(1L)).thenReturn(count);

        //when
        Long result = notificationService.countUnReadNotifications(1L);

        //then
        assertEquals(result, count);

    }

    @Test
    @DisplayName("읽은 알림 개수")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void countReadNotificationsTest() throws Exception{
        //given
        Long count = 5L;
        when(notificationRepository.countReadNotifications(1L)).thenReturn(count);

        //when
        Long result = notificationService.countReadNotifications(1L);

        //then
        assertEquals(result, count);

    }

    @Test
    @DisplayName("알림 개별 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void deleteNotiTest() throws Exception {
        //given
        doNothing().when(notificationRepository).deleteById(1L);

        //when
        notificationService.deleteNoti(1L);

        //then
        verify(notificationRepository, times(1)).deleteById(1L);

    }
    @Test
    @DisplayName("알림 전체 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void deleteAllNotisTest() throws Exception {
        //given
        doNothing().when(notificationRepository).deleteByReceiver(1L);

        //when
        notificationService.deleteAllNotis(1L);

        //then
        verify(notificationRepository, times(1)).deleteByReceiver(1L);

    }

}
