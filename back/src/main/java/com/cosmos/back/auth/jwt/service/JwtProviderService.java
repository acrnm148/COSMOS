package com.cosmos.back.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cosmos.back.auth.jwt.JwtToken;
import com.cosmos.back.auth.jwt.refreshToken.RefreshToken;
import com.cosmos.back.auth.jwt.JwtProperties;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * JWT 토큰 생성과 관련된 서비스
 */
@Service
//@RequiredArgsConstructor
public class JwtProviderService {

    /**
     * accessToken, refreshToken 생성
     */
    public JwtToken createJwtToken(Long userSeq, String userId) {

        //Access token 생성
        String accessToken = JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.AccessToken_TIME))
                .withClaim("userSeq", userSeq)
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        //Refresh token 생성
        String refreshToken = JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.RefreshToken_TIME))
                .withClaim("userSeq", userSeq)
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * access token 생성
     */
    public String createAccessToken(Long userSeq , String userId) {

        String accessToken = JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.AccessToken_TIME))
                .withClaim("userSeq", userSeq)
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return accessToken;
    }

    public String createRefreshToken(Long userSeq , String userId) {
        Date now = new Date();

        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.RefreshToken_TIME))
                .withClaim("userSeq", userSeq)
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }


    /**
     * refreshToken validation 체크(refresh token 이 넘어왔을때)
     * 정상 - access 토큰 생성후 반환
     * 비정상 - null
     */
    public String validRefreshToken(RefreshToken refreshToken) {

        String RefreshToken = refreshToken.getRefreshToken();

        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(RefreshToken);

            //refresh 토큰의 만료시간이 지나지 않아 access 토큰만 새로 생성
            if(!verify.getExpiresAt().before(new Date())) {
                String accessToken = createAccessToken(verify.getClaim("userSeq").asLong(), verify.getClaim("userId").asString());
                return accessToken;
            }
        }catch (Exception e) {

            //refresh 토큰이 만료됨
            return null;
        }
        return null;
    }

    /**
     * access 토큰의 만료시간
     */
    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(JwtProperties.SECRET.getBytes())
                .build().parseClaimsJws(accessToken).getBody().getExpiration();
        long now = new Date().getTime();
        return expiration.getTime() - now;
    }

}
