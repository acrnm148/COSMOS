package com.cosmos.back.controller;


import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.service.NotificationService;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
public class SseControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

}
