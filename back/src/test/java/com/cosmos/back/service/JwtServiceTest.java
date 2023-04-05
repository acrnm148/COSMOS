package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.auth.jwt.JwtToken;
import com.cosmos.back.auth.jwt.service.JwtProviderService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtProviderService jwtProviderService;

    @Test
    @DisplayName("accessToken, refreshToken 생성")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void createJwtTokenTest() throws Exception {
        //given
        JwtToken jwtToken = JwtToken.builder().accessToken("accessToken").refreshToken("refreshToken").build();

        //when
        JwtToken result = jwtProviderService.createJwtToken(1L, "testId");

        //then
        Assertions.assertThat(result.getAccessToken()).isNotEmpty();
        assertThat(result.getRefreshToken()).isNotEmpty();

    }

    @Test
    @DisplayName("accessToken 생성")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void createAccessTokenTest() throws  Exception {
        //when
        String result = jwtProviderService.createAccessToken(0L, "룰루");

        //then
        assertThat(result).isNotEmpty();

    }

    @Test
    @DisplayName("accessToken 생성")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void validRefreshTokenTest() throws Exception {
        //given
        JwtToken jwtTokenOK = jwtProviderService.createJwtToken(1L, "testId");
        JwtToken jwtTokenNo = jwtProviderService.createJwtToken(1L, "testId");

        //when
        String accessTokenOk = jwtProviderService.validRefreshToken(jwtTokenOK.getRefreshToken());

        //then
        System.out.println("accessTokenOk = " + accessTokenOk);
        assertThat(accessTokenOk).isNotEmpty();

    }

}
