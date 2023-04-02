package com.cosmos.back.controller;


import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.request.NotificationRequestDto;
import com.cosmos.back.dto.response.NotificationDto;
import com.cosmos.back.model.NotificationType;
import com.cosmos.back.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
public class SseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private JwtService jwtService;

    @Test
    @DisplayName("알림 구독(SSE에 연결)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void subscribeTest() throws Exception {
        //given
        SseEmitter emitter = new SseEmitter();
        emitter.send(SseEmitter.event()
                .name("connect")
                .id("123")
                .data("sse [userSeq=" + 1 + "]"));

        when(notificationService.subscribe(1L, "123")).thenReturn(emitter);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/noti/subscribe/1")
                .header("lastEventId", "123")).andDo(print());

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        assertThat(mvcResult.getResponse().getContentAsString().contains("data:sse [userSeq=1]"));

    }

    @Test
    @DisplayName("알림 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findAllNotificationsTest() throws Exception {
        //given
        List<NotificationDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NotificationDto dto = NotificationDto.builder().id((long) i).build();
            list.add(dto);
        }

        when(notificationService.findAllNotifications(1L)).thenReturn(list);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/noti/list/1"));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        NotificationDto[] responseData = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), NotificationDto[].class);
        assertThat(responseData.length).isEqualTo(5);
        verify(notificationService, times(1)).findAllNotifications(1L);
    }

    @Test
    @DisplayName("안 읽은 알림 개수")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void countUnReadNotificationsTest() throws Exception{
        //given
        Long count = 5L;

        when(notificationService.countUnReadNotifications(anyLong())).thenReturn(count);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/noti/unread/1"));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        long responseData = Long.parseLong(mvcResult.getResponse().getContentAsString());
        assertThat(responseData).isEqualTo(count);
    }

    @Test
    @DisplayName("읽은 알림 개수")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void countReadNotificationsTest() throws Exception{
        //given
        Long count = 3L;

        when(notificationService.countReadNotifications(1L)).thenReturn(count);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/noti/read/1"));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        long result = Long.parseLong(mvcResult.getResponse().getContentAsString());
        assertEquals(result, count);
    }

    @Test
    @DisplayName("알림 전송 테스트")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void sendNotificationsTest() throws Exception {
        //given
        NotificationRequestDto dto = NotificationRequestDto.builder().userSeq(1L).content("contentTest").url("urlTest").build();

        doNothing().when(notificationService).send(dto.getUserSeq(), NotificationType.MESSAGE, dto.getContent(), dto.getUrl());
        String content = objectMapper.writeValueAsString(dto);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/noti/sendTest").content(content).contentType(MediaType.APPLICATION_JSON));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();

        verify(notificationService, times(1)).send(dto.getUserSeq(), NotificationType.MESSAGE, dto.getContent(), dto.getUrl());
        assertEquals(mvcResult.getResponse().getContentAsString(), "전송 완료");
    }

    @Test
    @DisplayName("유저 알림 개별 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void deleteNotificationTest() throws Exception {
        //given
        String token = "123456789101112";
        JwtState success = JwtState.SUCCESS;
        JwtState mismatch = JwtState.MISMATCH_USER;
        JwtState expired = JwtState.EXPIRED_ACCESS;

        Map<String ,String > mismatchMessage = new LinkedHashMap<>();
        mismatchMessage.put("status", "401");
        mismatchMessage.put("message", "유저 불일치");

        Map<String, String> expiredMessage = new LinkedHashMap<>();
        expiredMessage.put("status", "401");
        expiredMessage.put("message", "accessToken 만료 또는 잘못된 값입니다.");

        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString())).thenReturn(success)
                .thenReturn(mismatch)
                .thenReturn(expired);
        when(jwtService.mismatchUserResponse()).thenReturn(mismatchMessage);
        when(jwtService.requiredRefreshTokenResponse()).thenReturn(expiredMessage);
        doNothing().when(notificationService).deleteNoti(anyLong());

        //when
        ResultActions successResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/noti/del/1/1")
                .header(HttpHeaders.AUTHORIZATION, token));
        ResultActions misMatchResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/noti/del/1/1")
                .header(HttpHeaders.AUTHORIZATION, token));
        ResultActions expiredResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/noti/del/1/1")
                .header(HttpHeaders.AUTHORIZATION, token));

        //then
        MvcResult successResponse = successResult.andExpect(status().isOk()).andReturn();
        assertThat(successResponse.getResponse().getContentAsString()).isEqualTo("삭제 완료");

        MvcResult misMatchResponse = misMatchResult.andExpect(status().isOk()).andReturn();
        Map mismatchResponseMessage = new Gson().fromJson(misMatchResponse.getResponse().getContentAsString(), Map.class);
        assertThat(mismatchResponseMessage.get("message")).isEqualTo("유저 불일치");

        MvcResult expiredResponse = expiredResult.andExpect(status().isOk()).andReturn();
        Map expiredResponseMessage = new Gson().fromJson(expiredResponse.getResponse().getContentAsString(), Map.class);
        assertThat(expiredResponseMessage.get("message")).isEqualTo("accessToken 만료 또는 잘못된 값입니다.");

    }
    @Test
    @DisplayName("유저 알림 전체 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void deleteAllNotificationsTest() throws Exception {
        //given
        String token = "123456789101112";
        JwtState success = JwtState.SUCCESS;
        JwtState mismatch = JwtState.MISMATCH_USER;
        JwtState expired = JwtState.EXPIRED_ACCESS;

        Map<String ,String > mismatchMessage = new LinkedHashMap<>();
        mismatchMessage.put("status", "401");
        mismatchMessage.put("message", "유저 불일치");

        Map<String, String> expiredMessage = new LinkedHashMap<>();
        expiredMessage.put("status", "401");
        expiredMessage.put("message", "accessToken 만료 또는 잘못된 값입니다.");

        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString())).thenReturn(success)
                .thenReturn(mismatch)
                .thenReturn(expired);
        when(jwtService.mismatchUserResponse()).thenReturn(mismatchMessage);
        when(jwtService.requiredRefreshTokenResponse()).thenReturn(expiredMessage);
        doNothing().when(notificationService).deleteAllNotis(anyLong());

        //when
        ResultActions successResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/noti/delAll/1")
                .header(HttpHeaders.AUTHORIZATION, token));
        ResultActions misMatchResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/noti/delAll/1")
                .header(HttpHeaders.AUTHORIZATION, token));
        ResultActions expiredResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/noti/delAll/1")
                .header(HttpHeaders.AUTHORIZATION, token));

        //then
        MvcResult successResponse = successResult.andExpect(status().isOk()).andReturn();
        assertThat(successResponse.getResponse().getContentAsString()).isEqualTo("삭제 완료");

        MvcResult misMatchResponse = misMatchResult.andExpect(status().isOk()).andReturn();
        Map mismatchResponseMessage = new Gson().fromJson(misMatchResponse.getResponse().getContentAsString(), Map.class);
        assertThat(mismatchResponseMessage.get("message")).isEqualTo("유저 불일치");

        MvcResult expiredResponse = expiredResult.andExpect(status().isOk()).andReturn();
        Map expiredResponseMessage = new Gson().fromJson(expiredResponse.getResponse().getContentAsString(), Map.class);
        assertThat(expiredResponseMessage.get("message")).isEqualTo("accessToken 만료 또는 잘못된 값입니다.");

    }

}
