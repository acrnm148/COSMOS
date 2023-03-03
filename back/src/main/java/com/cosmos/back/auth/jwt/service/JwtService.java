package com.cosmos.back.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cosmos.back.auth.jwt.JwtToken;
import com.cosmos.back.auth.jwt.refreshToken.RefreshToken;
import com.cosmos.back.model.User;
import com.cosmos.back.repository.UserRepository;
import com.cosmos.back.auth.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 실제 JWT 토큰과 관련된 서비스
 * refresh 토큰을 검사 -> 유효하면 access
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtService {

    private final JwtProviderService jwtProviderService;
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    /**
     * access, refresh 토큰 생성
     * userSeq, userId를 넣음
     */
    @Transactional
    public JwtToken getJwtToken(String userId) {

        User user = userRepository.findByUserId(userId);
        RefreshToken userRefreshToken = user.getJwtRefreshToken();

        //처음 서비스를 이용하는 사용자(refresh 토큰이 없는 사용자)
        if(userRefreshToken ==null) {

            //access, refresh 토큰 생성
            JwtToken jwtToken = jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId());

            //방법1. DB에 저장(refresh 토큰 저장)
            //refreshToken 엔티티 생성
            //RefreshToken refreshToken = new RefreshToken(jwtToken.getRefreshToken());
            //user.createRefreshToken(refreshToken);
            //방법2. Redis에 저장(refresh 토큰 저장)
            ValueOperations<String, String> loginValueOperations = redisTemplate.opsForValue();
            //loginValueOperations.set(accessToken, "logout"); // redis set 명령어
            loginValueOperations.set(userId, jwtToken.getRefreshToken()); // redis set 명령어


            return jwtToken;
        }
        //refresh 토큰이 있는 사용자(기존 사용자)
        else {

            //유효하면 accesstoken 받아옴, 만료되면 null
            String accessToken = jwtProviderService.validRefreshToken(userRefreshToken);

            //refresh 토큰 기간이 유효
            if(accessToken !=null) {
                return new JwtToken(accessToken, userRefreshToken.getRefreshToken());
            }
            else { //refresh 토큰 기간만료
                //새로운 access, refresh 토큰 생성
                JwtToken newJwtToken = jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId());

                user.SetRefreshToken(newJwtToken.getRefreshToken());
                return newJwtToken;
            }
        }
    }

    /**
     * access 토큰 validate
     */
    public String validAccessToken(String accessToken) {

        try {
            //복호화
            DecodedJWT verify = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(accessToken);
            if(!verify.getExpiresAt().before(new Date())) {
                return verify.getClaim("userId").asString();
            }

        }catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * refresh 토큰 validate
     */
    @Transactional
    public JwtToken validRefreshToken(String userid, String refreshToken) {

        User user = userRepository.findByUserId(userid);

        //전달받은 refresh 토큰과 DB의 refresh 토큰이 일치하는지 확인
        RefreshToken findRefreshToken = sameCheckRefreshToken(user, refreshToken);

        //refresh 토큰이 만료되지 않았으면 access 토큰은 null이 아니다.
        String accessToken = jwtProviderService.validRefreshToken(findRefreshToken);

        //refresh 토큰의 유효기간이 남아 access 토큰만 생성
        if(accessToken!=null) {
            return new JwtToken(accessToken, refreshToken);
        }
        //refresh 토큰이 만료됨 -> access, refresh 토큰 모두 재발급
       else {
            JwtToken newJwtToken = jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId());
            user.SetRefreshToken(newJwtToken.getRefreshToken());
            return newJwtToken;
        }

    }

    /**
     * refreshtoken 중복 확인
     */
    public RefreshToken sameCheckRefreshToken(User user, String refreshToken) {

        //DB 에서 찾기
        RefreshToken jwtRefreshToken = user.getJwtRefreshToken();

        if(jwtRefreshToken.getRefreshToken().equals(refreshToken)){
            return jwtRefreshToken;
        }
        return null;
    }

    /**
     * json response
     */
    //로그인 성공
    public Map<String , String> successLoginResponse(JwtToken jwtToken) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status", "200");
        map.put("message", "accessToken, refreshToken 생성 완료");
        map.put("accessToken", jwtToken.getAccessToken());
        map.put("refreshToken", jwtToken.getRefreshToken());
        return map;
    }

    //인증 요구 json response (jwt 토큰이 필요한 요구)
    public Map<String, String> requiredJwtTokenResponse() {
        Map<String ,String> map = new LinkedHashMap<>();
        map.put("status", "401");
        map.put("message", "로그인 해야합니다.");
        return map;
    }

    //accessToken이 만료된 경우의 reponse
    public Map<String, String> requiredRefreshTokenResponse() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status", "401");
        map.put("message", "accessToken 만료 또는 잘못된 값입니다.");
        return map;
    }

    //refresh 토큰 재발급 response
    public Map<String, String> recreateTokenResponse(JwtToken jwtToken) {
        Map<String ,String > map = new LinkedHashMap<>();
        map.put("status", "200");
        map.put("message", "refreshToken, accessToken 재발급 완료");
        map.put("accessToken", jwtToken.getAccessToken());
        map.put("refreshToken", jwtToken.getRefreshToken());
        return map;
    }

}
