package com.cosmos.back.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cosmos.back.annotation.Generated;
import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.JwtToken;
import com.cosmos.back.model.User;
import com.cosmos.back.repository.user.UserRepository;
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
    public JwtToken getJwtToken(Long userSeq) {

        User user = userRepository.findByUserSeq(userSeq);
        String redisUserSeq = userSeq.toString();

        ValueOperations<String, String> loginValue = redisTemplate.opsForValue();
        String refreshToken = loginValue.get(redisUserSeq);

        //처음 서비스를 이용하는 사용자(refresh 토큰이 없는 사용자 = redis에 저장되지 않은 사용자)
        if(refreshToken==null) { //redis에 저장되지 않았으면

            //access, refresh 토큰 생성
            JwtToken jwtToken = jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId());
            //Redis에 refresh 토큰 저장
            loginValue.set(redisUserSeq, jwtToken.getRefreshToken()); // redis set 명령어. (2543509554, exkdjf.kdsj.aflkd) 이런 형태로 저장

            return jwtToken;
        }
        //refresh 토큰이 있는 사용자(기존 사용자)
        else {

            //유효하면 access 토큰 받아옴, 만료되면 null
            String accessToken = jwtProviderService.validRefreshToken(refreshToken);

            //refresh 토큰 유효할 때
            if(accessToken !=null) {
                return new JwtToken(accessToken, refreshToken);
            }
            else { // refresh 토큰 기간 만료
                //새로운 access, refresh 토큰 생성
                JwtToken newJwtToken = jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId());

                return newJwtToken;
            }
        }
    }

    /**
     * access 토큰 validate
     */
    public Long validAccessToken(String accessToken) {
        try {
            //복호화
            DecodedJWT verify = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(accessToken);
            if(!verify.getExpiresAt().before(new Date())) {
                return verify.getClaim("userSeq").asLong();
            }
        }catch (Exception e) {
            System.out.println("[jwtService] access 토큰 만료");
            return null;
        }
        return null;
    }

    /**
     * userSeq와 token의 userSeq 일치 여부 체크
     *  & access토큰 유효기간 체크
     */
    public JwtState checkUserSeqWithAccess(Long userSeq, String accessToken) {
        try {
            //복호화
            DecodedJWT verify = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(accessToken);
            if(!verify.getExpiresAt().before(new Date())) {
                Long findUserSeq = verify.getClaim("userSeq").asLong();
                System.out.println("userSeq by token:"+findUserSeq +" / userSeq: "+userSeq);
                if (findUserSeq.equals(userSeq)) {
                    return JwtState.SUCCESS;
                }
                return JwtState.MISMATCH_USER;
            }
        }catch (Exception e) { //유효기간 지났을 때
            System.out.println("[jwtService] access 토큰 만료");
            return JwtState.EXPIRED_ACCESS;
        }
        return null;
    }

    /**
     * [refresh 토큰 validate]
     * access 토큰이 만료되었을 때 redis에서 refresh토큰 유효기간 체크 후
     * access 토큰만 만료 -> access 토큰만 재발급
     * refresh 토큰도 만료 -> access, refresh 둘 다 재발급
     */
    @Transactional
    public JwtToken validRefreshToken(Long userSeq) {

        User user = userRepository.findByUserSeq(userSeq);
        String redisUserSeq = userSeq.toString();

        ValueOperations<String, String> loginValue = redisTemplate.opsForValue();
        String refreshToken = loginValue.get(redisUserSeq);

        //refresh 토큰이 만료되지 않았으면 access 토큰은 null이 아니다.
        String accessToken = jwtProviderService.validRefreshToken(refreshToken);

        //refresh 토큰의 유효기간이 남아 access 토큰만 생성
        if(accessToken!=null) {
            System.out.println("refresh 토큰 유효");
            return new JwtToken(accessToken, refreshToken);
        }
        //refresh 토큰이 만료됨 -> access, refresh 토큰 모두 재발급
        else {
            System.out.println("refresh 토큰 만료");
            //jwt 생성
            JwtToken newJwtToken = jwtProviderService.createJwtToken(user.getUserSeq(), user.getUserId());

            //redis에 있던 refresh토큰 삭제
            loginValue.getAndDelete(redisUserSeq);

            //redis에 새로 생성한 refresh토큰 저장
            loginValue.set(redisUserSeq, newJwtToken.getRefreshToken());

            return newJwtToken;
        }

    }

    /**
     * accesstoken 복호화해서 유저 시퀀스 추출
     */
    @Transactional
    @Generated
    public Long getUserSeq(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
        Long userSeq = decodedJWT.getClaim("userSeq").asLong();
        return userSeq;
    }

    /**
     * accesstoken 복호화해서 유저 아이디 추출
     */
    @Transactional
    @Generated
    public String getUserId(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
        String userId = decodedJWT.getClaim("userId").asString();
        return userId;
    }

    /**
     * json response
     */
    //로그인 성공
    @Generated
    public Map<String , String> successLoginResponse(JwtToken jwtToken, Long userSeq, Long coupleId) {
        if (coupleId == null) coupleId = 0L;
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status", "200");
        map.put("message", "accessToken, refreshToken 생성 완료");
        map.put("accessToken", jwtToken.getAccessToken());
        map.put("refreshToken", jwtToken.getRefreshToken());
        map.put("userSeq", userSeq.toString());
        map.put("coupleId", coupleId.toString());
        return map;
    }

    //인증 요구 json response (jwt 토큰이 필요한 요구)
    @Generated
    public Map<String, String> requiredJwtTokenResponse() {
        Map<String ,String> map = new LinkedHashMap<>();
        map.put("status", "401");
        map.put("message", "로그인 해야합니다.");
        return map;
    }

    //accessToken이 만료된 경우의 reponse
    @Generated
    public Map<String, String> requiredRefreshTokenResponse() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("status", "401");
        map.put("message", "accessToken 만료 또는 잘못된 값입니다.");
        return map;
    }

    //refresh 토큰 재발급 response
    @Generated
    public Map<String, String> recreateTokenResponse(JwtToken jwtToken) {
        Map<String ,String > map = new LinkedHashMap<>();
        map.put("status", "200");
        map.put("message", "refreshToken, accessToken 재발급 완료");
        map.put("accessToken", jwtToken.getAccessToken());
        map.put("refreshToken", jwtToken.getRefreshToken());
        return map;
    }

    //userSeq와 토큰의 userSeq 불일치
    @Generated
    public Map<String, String> mismatchUserResponse() {
        Map<String ,String > map = new LinkedHashMap<>();
        map.put("status", "401");
        map.put("message", "유저 불일치");
        return map;
    }
}
