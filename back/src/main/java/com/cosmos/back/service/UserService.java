package com.cosmos.back.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.UserProfileDto;
import com.cosmos.back.dto.UserUpdateDto;
import com.cosmos.back.repository.UserRepository;
import com.cosmos.back.auth.jwt.JwtProperties;
import com.cosmos.back.auth.jwt.service.JwtProviderService;
import com.cosmos.back.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    /**
     * 유저 정보 조회
     * accessToken을 복호화(디코딩)해서 유저 정보 가져옴
     */
    @Transactional
    public UserProfileDto getUser(Long userSeq, String accessToken) {
        System.out.println("getUser 함수 진입");
        try {
            //복호화된 JWT
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(accessToken);
            Long userSeqByToken = decodedJWT.getClaim("userSeq").asLong();
            if (userSeq != userSeqByToken) {
                return null;
            }

            User user = userRepository.findByUserSeq(userSeq);

            UserProfileDto userProfileDto = UserProfileDto.builder()
                    .userSeq(user.getUserSeq())
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .phoneNumber(user.getPhoneNumber())
                    .profileImgUrl(user.getProfileImgUrl())
                    .coupleYn(user.getCoupleYn())
                    .ageRange(user.getAgeRange())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .role("USER")
                    .type1(user.getType1())
                    .type2(user.getType2())
                    .coupleUserId(user.getCoupleUserId())
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
//        user.setTel(dto.getTel());
//        user.setZipCode(dto.getZipcode());
//        user.setNickName(dto.getNickname());
//        user.setAddr1(dto.getAddr1());
//        user.setAddr2(dto.getAddr2());

        userRepository.save(user);
        return user;
    }

    /**
     * Redis를 이용한 로그아웃
     */
    @Transactional
    public boolean logout(Long userSeq, String accessToken) {
        //accesstoken 복호화해서 나온 userId가 userId와 동일하면 ok, 다르면 응답안함
        if (jwtService.getUserSeq(accessToken) == userSeq) {
            return true;
        }
        return false;
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
