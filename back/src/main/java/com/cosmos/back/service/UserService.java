package com.cosmos.back.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cosmos.back.dto.UserProfileDto;
import com.cosmos.back.dto.UserUpdateDto;
import com.cosmos.back.dto.request.JoinRequestDto;
import com.cosmos.back.dto.response.JoinResponseDto;
import com.cosmos.back.repository.UserRepository;
import com.cosmos.back.auth.jwt.JwtProperties;
import com.cosmos.back.auth.jwt.refreshToken.RefreshToken;
import com.cosmos.back.auth.jwt.refreshToken.RefreshTokenRepository;
import com.cosmos.back.auth.jwt.service.JwtProviderService;
import com.cosmos.back.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 실제 JWT 토큰과 관련된 서비스
 * refresh 토큰을 검사 -> 유효하면 access
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtProviderService jwtProviderService; //얘 추가하고 오류

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final StringRedisTemplate redisTemplate;

    /**
     * 유저 정보 조회
     * accessToken을 복호화(디코딩)해서 유저 정보 가져옴
     */
    @Transactional
    public UserProfileDto getUser(String accessToken) {
        System.out.println("getUser 함수 진입");
        try {
            //복호화된 JWT
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(accessToken);
            String userid = decodedJWT.getClaim("userid").asString();
            User user = userRepository.findByUserId(userid);

            System.out.println("현재 암호화된 비밀번호: "+user.getPassword());

            UserProfileDto userProfileDto = UserProfileDto.builder()
                    .userId(user.getUserId())
                    .profileImg(user.getProfileImg())
                    .tel(user.getTel())
                    .username(user.getUserName())
                    .nickname(user.getNickName())
                    .addr1(user.getAddr1())
                    .addr2(user.getAddr2())
                    .zipcode(user.getZipCode())
                    .birth(user.getBirth())
                    .birthYear(user.getBirthYear())
                    .gender(user.getGender())
                    .roles("USER")
                    .email(user.getEmail())
                    .provider(user.getProvider())
                    .createTime(LocalDateTime.now())
                    .build();

            System.out.println("유저 프로필 : "+userProfileDto);
            return userProfileDto;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 유저 정보 수정
     * 프로필이미지 변경, 비밀번호 변경
     */
    @Transactional
    public User updateUserInfo(UserUpdateDto dto) {
        User user = userRepository.findByUserId(dto.getUserId());
        if (dto.getTel() == null || dto.getNickname() == null ||
           dto.getZipcode() == null || dto.getAddr1() == null ||
            dto.getAddr2()==null) {
            System.out.println("입력란이 비었습니다.");
            return null;
        }
        user.setTel(dto.getTel());
        user.setZipCode(dto.getZipcode());
        user.setNickName(dto.getNickname());
        user.setAddr1(dto.getAddr1());
        user.setAddr2(dto.getAddr2());

        userRepository.save(user);
        return user;
    }

    /**
     * Redis를 이용한 로그아웃
     */
    @Transactional
    public void logout(String accessToken) {
        UserProfileDto user = getUser(accessToken);
        String refreshToken = user.getRefreshToken();

        //1. accessToken redisTemplate 블랙리스트 추가
        //ValueOperations<String, String> logoutValueOperations = redisTemplate.opsForValue();
        //logoutValueOperations.set(accessToken, "logout"); // redis set 명령어

        //2. refreshToken 삭제
        //RefreshToken rf = refreshTokenRepository.findByRefreshToken(refreshToken).get();
        //User userEntity = userRepository.findByUserId(user.getUserId());
        //userEntity.setJwtRefreshToken(null);//부모에서 삭제
        //refreshTokenRepository.deleteById(rf.getId());//자식에서 삭제
    }

    
    /**
     * accesstoken 복호화해서 유저 아이디 추출
     */
    @Transactional
    public String getUserid(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
        String userid = decodedJWT.getClaim("userId").asString();
        return userid;
    }

    /**
     * 중복 유저 체크
     */
    public boolean validateDuplicated(String userid) {
        if (userRepository.findByUserId(userid) != null) { //중복되면 true
            return true;
        }
        return false;
    }

}
