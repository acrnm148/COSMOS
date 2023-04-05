package com.cosmos.back.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.auth.jwt.JwtProperties;
import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.JwtToken;
import com.cosmos.back.auth.jwt.service.JwtProviderService;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.model.User;
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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Date;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
public class JwtServiceTest {

    @SpyBean
    private JwtProviderService jwtProviderService;

    @Autowired
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;


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
    @DisplayName("refreshToken validation 체크")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void validRefreshTokenTest() throws Exception {
        //given
        JwtToken jwtTokenOK = jwtProviderService.createJwtToken(1L, "testId");
        JwtToken jwtTokenError = jwtProviderService.createJwtToken(1L, "testId");
        String refreshToken = JWT.create()
                .withSubject("testId")
                .withExpiresAt(new Date(System.currentTimeMillis() - 1000))
                .withClaim("userSeq", 1L)
                .withClaim("userId", "testId")
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        jwtTokenError.setRefreshToken(refreshToken);

        //when
        String accessTokenOk = jwtProviderService.validRefreshToken(jwtTokenOK.getRefreshToken());
        String accessTokenError = jwtProviderService.validRefreshToken(jwtTokenError.getRefreshToken());

        //then
        assertThat(accessTokenOk).isNotEmpty();
        assertThat(accessTokenError).isNull();
    }

    @Test
    @DisplayName("access, refresh 토큰 생성")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void getJwtTokenTest() throws Exception {
        //given
        User userRefreshNull = User.builder().userSeq(2L).userId("userId").build();
        JwtToken jwtToken = JwtToken.builder().accessToken("accessToken").refreshToken("refreshToken").build();
        ValueOperations<String, String> loginValue = redisTemplate.opsForValue();
        loginValue.getAndDelete(userRefreshNull.getUserSeq().toString());

        when(userRepository.findByUserSeq(2L)).thenReturn(userRefreshNull);
        when(jwtProviderService.createJwtToken(userRefreshNull.getUserSeq(), userRefreshNull.getUserId())).thenReturn(jwtToken);
        when(jwtProviderService.validRefreshToken("refreshToken"))
                .thenReturn(null)
                .thenReturn("accesstoken");

        //when
        JwtToken resultRefreshTokenNull = jwtService.getJwtToken(2L);
        JwtToken resultRefreshTokenOK = jwtService.getJwtToken(2L);
        JwtToken resultRefreshTokenExpired = jwtService.getJwtToken(2L);

        //then
        assertThat(resultRefreshTokenNull.getAccessToken()).isEqualTo(jwtToken.getAccessToken());
        assertThat(resultRefreshTokenNull.getRefreshToken()).isEqualTo(jwtToken.getRefreshToken());
        assertThat(resultRefreshTokenOK.getAccessToken()).isNotNull();
        assertThat(resultRefreshTokenExpired.getRefreshToken()).isEqualTo(jwtToken.getRefreshToken());
    }

    @Test
    @DisplayName("access 토큰 validate")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void validAccessTokenTest() throws Exception {
        //given
        User user = User.builder().userSeq(-100L).userId("userId").build();
        JwtToken jwtToken = jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId());
        String accessToken = jwtToken.getAccessToken();
        String accessTokenExpired = JWT.create()
                .withSubject(user.getUserId())
                .withExpiresAt(new Date(System.currentTimeMillis() - 1000))
                .withClaim("userSeq", user.getUserSeq())
                .withClaim("userId", user.getUserId())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        //then
        Long result = jwtService.validAccessToken(accessToken);
        Long resultExpired = jwtService.validAccessToken(accessTokenExpired);

        //when
        assertThat(result).isEqualTo(user.getUserSeq());
        assertThat(resultExpired).isNull();

    }

    @Test
    @DisplayName("userSeq와 token의 userSeq 일치 여부 체크")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void checkUserSeqWithAccessTest() throws Exception {
        //given
        User user = User.builder().userSeq(-100L).userId("userId").build();
        JwtToken jwtToken = jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId());
        String accessToken = jwtToken.getAccessToken();
        String accessTokenExpired = JWT.create()
                .withSubject(user.getUserId())
                .withExpiresAt(new Date(System.currentTimeMillis() - 1000))
                .withClaim("userSeq", user.getUserSeq())
                .withClaim("userId", user.getUserId())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));


        JwtState success = JwtState.SUCCESS;
        JwtState mismatchUser = JwtState.MISMATCH_USER;
        JwtState expiredAccess = JwtState.EXPIRED_ACCESS;


        //when
        JwtState resultSuccess = jwtService.checkUserSeqWithAccess(user.getUserSeq(), accessToken);
        JwtState resultMismatch = jwtService.checkUserSeqWithAccess(1L, accessToken);
        JwtState resultExpired = jwtService.checkUserSeqWithAccess(user.getUserSeq(), accessTokenExpired);

        //then
        assertEquals(resultSuccess, success);
        assertEquals(resultMismatch, mismatchUser);
        assertEquals(resultExpired, expiredAccess);

    }

    @Test
    @DisplayName("refresh 토큰 validate")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void JwtServicevalidRefreshTokenTest() throws Exception {
        //given
        User user = User.builder().userSeq(-100L).userId("userId").build();
        JwtToken jwtToken = jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId());
        ValueOperations<String, String> loginValue = redisTemplate.opsForValue();
        loginValue.set(user.getUserSeq().toString(), jwtToken.getRefreshToken());

        when(userRepository.findByUserSeq(-100L)).thenReturn(user);
        when(jwtProviderService.validRefreshToken(jwtToken.getRefreshToken()))
                .thenReturn(jwtToken.getAccessToken())
                .thenReturn(null);
        when(jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId())).thenReturn(jwtToken);

        //when
        JwtToken jwtTokenOk = jwtService.validRefreshToken(-100L);
        JwtToken jwtTokenExpired = jwtService.validRefreshToken(-100L);

        //then
        assertEquals(jwtTokenOk.getAccessToken(), jwtToken.getAccessToken());
        assertEquals(jwtTokenExpired.getAccessToken(), jwtToken.getAccessToken());

    }

}
