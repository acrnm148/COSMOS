package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.user.UserProfileDto;
import com.cosmos.back.oauth.service.KakaoService;
import com.cosmos.back.service.PlaceService;
import com.cosmos.back.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private KakaoService kakaoService;

    @Test
    @DisplayName("UserController 회원 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 회원조회() throws Exception {
        //UserProfileDto userProfileDto = userService.getUser(userSeq);
//        UserProfileDto mockDto = UserProfileDto.builder().userName("테스트").build();
//
//        //mocking
//        when(userService.getUser(anyLong()))
//                .thenReturn(mockDto);
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/accounts/userInfo/1")
//                .accept(MediaType.APPLICATION_JSON);
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        System.out.println("response:"+response);
//        //Assertions.assertThat(response).contatins("축제");
    }

    @Test
    @DisplayName("UserController 회원 정보 수정")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 회원정보수정() throws Exception {

    }

    @Test
    @DisplayName("UserController 로그아웃")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 로그아웃() throws Exception {

    }

    @Test
    @DisplayName("UserController 카카오로그인")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 카카오로그인() throws Exception {

    }

    @Test
    @DisplayName("UserController 카카오코드발급")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 카카오코드발급() throws Exception {

    }

    @Test
    @DisplayName("UserController access토큰재발급")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void access토큰재발급() throws Exception {

    }

    @Test
    @DisplayName("UserController 커플연결수락")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 커플연결수락() throws Exception {

    }

    @Test
    @DisplayName("UserController 커플연결끊기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 커플연결끊기() throws Exception {

    }

    @Test
    @DisplayName("UserController 사용자유형등록")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 사용자유형등록() throws Exception {

    }

    @Test
    @DisplayName("UserController 난수생성후리턴")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 난수생성후리턴() throws Exception {

    }
}
