package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.JwtToken;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.user.UserProfileDto;
import com.cosmos.back.model.User;
import com.cosmos.back.oauth.provider.Token.KakaoToken;
import com.cosmos.back.oauth.service.KakaoService;
import com.cosmos.back.service.NotificationService;
import com.cosmos.back.service.PlaceService;
import com.cosmos.back.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @MockBean
    private NotificationService notificationService;

    @Test
    @DisplayName("UserController 회원 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 회원조회() throws Exception {
        //given
        String token = "1231231231312313213123123123";
        JwtState success = JwtState.SUCCESS;
        JwtState mismatchUser = JwtState.MISMATCH_USER;
        JwtState expiredAccess = JwtState.EXPIRED_ACCESS;

        // mismatchMap 생성
        Map<String ,String > mismatchMap = new LinkedHashMap<>();
        mismatchMap.put("status", "401");
        mismatchMap.put("message", "유저 불일치");

        // accesstoken 만료 시
        Map<String, String> expiredMap = new LinkedHashMap<>();
        expiredMap.put("status", "401");
        expiredMap.put("message", "accessToken 만료 또는 잘못된 값입니다.");

        UserProfileDto userProfileDto = UserProfileDto.builder().userName("suna").build();

        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString())).thenReturn(success)
                .thenReturn(mismatchUser)
                .thenReturn(expiredAccess)
                .thenReturn(success);

        // mismatchUserResponse 함수 실행 시 stubbing
        when(jwtService.mismatchUserResponse()).thenReturn(mismatchMap);

        // expiredResponse 함수 실행 시 stubbing
        when(jwtService.requiredRefreshTokenResponse()).thenReturn(expiredMap);


        when(userService.getUser(anyLong())).thenReturn(userProfileDto)
                .thenThrow(new NullPointerException());

        //when
        //success인 경우
        ResultActions successRequest = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/accounts/userInfo/1")
                        .header(HttpHeaders.AUTHORIZATION, token)).andDo(print());

        //mismatch인 경우
        ResultActions mismatchUserRequest = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/accounts/userInfo/1")
                        .header(HttpHeaders.AUTHORIZATION, token)).andDo(print());

        //expired인 경우
        ResultActions expiredAccessRequest = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/accounts/userInfo/1")
                        .header(HttpHeaders.AUTHORIZATION, token)).andDo(print());

        //exception인 경우
        ResultActions exceptionRequest = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/accounts/userInfo/1")
                .header(HttpHeaders.AUTHORIZATION, token)).andDo(print());

        //then
        //success
        MvcResult successResult = successRequest.andExpect(status().isOk()).andReturn();
        UserProfileDto result = new Gson().fromJson(successResult.getResponse().getContentAsString(), UserProfileDto.class);
        assertThat(result.getUserName()).isEqualTo(userProfileDto.getUserName());

        //mismatch
        MvcResult mismatchResult = mismatchUserRequest.andExpect(status().isOk()).andReturn();
        Map mismatchResponse = new Gson().fromJson(mismatchResult.getResponse().getContentAsString(), Map.class);
        assertThat(mismatchResponse.get("message")).isEqualTo("유저 불일치");

        //expired
        MvcResult expiredResult = expiredAccessRequest.andExpect(status().isOk()).andReturn();
        Map expiredResponse = new Gson().fromJson(expiredResult.getResponse().getContentAsString(), Map.class);
        assertThat(expiredResponse.get("message")).isEqualTo("accessToken 만료 또는 잘못된 값입니다.");

        //exception
        MvcResult exceptionResult = exceptionRequest.andExpect(status().isNotFound()).andReturn();
        assertThat(exceptionResult.getResponse().getContentAsString()).isEqualTo("");
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
        //given
        String token = "abcdefghijklmnop";
        JwtState success = JwtState.SUCCESS;
        JwtState mismatch = JwtState.MISMATCH_USER;
        JwtState expiredAccess = JwtState.EXPIRED_ACCESS;

        User user = User.builder().userSeq(1L).build();

        // mismatchMap 생성
        Map<String ,String > mismatchMap = new LinkedHashMap<>();
        mismatchMap.put("status", "401");
        mismatchMap.put("message", "유저 불일치");

        // accesstoken 만료 시
        Map<String, String> expiredMap = new LinkedHashMap<>();
        expiredMap.put("status", "401");
        expiredMap.put("message", "accessToken 만료 또는 잘못된 값입니다.");

        //mocking
        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString()))
                .thenReturn(success)
                .thenReturn(mismatch)
                .thenReturn(expiredAccess);
        doNothing().when(userService).logout(user.getUserSeq());

        when(jwtService.mismatchUserResponse()).thenReturn(mismatchMap);

        when(jwtService.requiredRefreshTokenResponse()).thenReturn(expiredMap);


        //when
        //성공 request
        ResultActions successRequest = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/accounts/logout/1")
                .header(HttpHeaders.AUTHORIZATION, token)).andDo(print());

        //mistmatch request
        ResultActions mismatchRequest = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/accounts/logout/1")
                .header(HttpHeaders.AUTHORIZATION, token)).andDo(print());

        //expired request
        ResultActions expiredRequest = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/accounts/logout/1")
                .header(HttpHeaders.AUTHORIZATION, token)).andDo(print());

        //then
        // 성공할 경우
        MvcResult successResponse = successRequest.andExpect(status().isOk()).andReturn();
        String successResult = successResponse.getResponse().getContentAsString();
        assertEquals(successResult, "로그아웃 되었습니다.");

        // Mismatch인 경우
        MvcResult mismatchResponse = mismatchRequest.andExpect(status().isOk()).andReturn();
        Map mismatchResult = new Gson().fromJson(mismatchResponse.getResponse().getContentAsString(), Map.class);
        assertEquals(mismatchResult.get("message"), "유저 불일치");

        // Expired인 경우
        MvcResult expiredResponse = expiredRequest.andExpect(status().isOk()).andReturn();
        Map expiredResult = new Gson().fromJson(expiredResponse.getResponse().getContentAsString(), Map.class);
        assertEquals(expiredResult.get("message"),"accessToken 만료 또는 잘못된 값입니다.");

    }

    @Test
    @DisplayName("UserController 카카오로그인")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 카카오로그인() throws Exception {
        //given
        // 변수 설정
        KakaoToken kakaoToken = new KakaoToken();
        kakaoToken.setAccess_token("token");
        User user = User.builder().userSeq(1L).userName("suna").build();
        JwtToken jwtToken = JwtToken.builder().build();
        SseEmitter sseEmitter = new SseEmitter();
        Map<String, String> successResponse = new LinkedHashMap<>();
        successResponse.put("status", "200");

        // 동작 mocking
        when(kakaoService.getAccessToken(anyString())).thenReturn(kakaoToken);
        when(kakaoService.saveUser(kakaoToken.getAccess_token())).thenReturn(user)
                .thenReturn(null);
        when(jwtService.getJwtToken(user.getUserSeq())).thenReturn(jwtToken);
        when(notificationService.subscribe(user.getUserSeq(), null)).thenReturn(sseEmitter);
        when(jwtService.successLoginResponse(jwtToken, user.getUserSeq(), user.getCoupleId())).thenReturn(successResponse);

        //when
        ResultActions resultActionsSuccess = mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/auth/login/kakao").param("code", "token"));
        ResultActions resultActionsFail = mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/auth/login/kakao").param("code", "token"));

        //then
        MvcResult mvcResultSuccess = resultActionsSuccess.andExpect(status().isOk()).andReturn();
        Map responseSuccess = new Gson().fromJson(mvcResultSuccess.getResponse().getContentAsString(), Map.class);
        assertEquals(responseSuccess.get("status"), "200");

        MvcResult mvcResultFail = resultActionsFail.andExpect(status().isOk()).andReturn();
        Map responseFail = new Gson().fromJson(mvcResultFail.getResponse().getContentAsString(), Map.class);
        assertEquals(responseFail.get("code"), "444");
    }

//    @Test
//    @DisplayName("UserController 카카오코드발급")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 카카오코드발급() throws Exception {
//    }

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
