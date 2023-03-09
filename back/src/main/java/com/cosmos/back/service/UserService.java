package com.cosmos.back.service;

import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.UserProfileDto;
import com.cosmos.back.dto.UserUpdateDto;
import com.cosmos.back.dto.request.TypeRequestDto;
import com.cosmos.back.repository.user.UserRepository;
import com.cosmos.back.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    public UserProfileDto getUser(Long userSeq) {
        System.out.println("getUser 함수 진입");
        try {
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
                    .coupleUserId(user.getCoupleId())
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
     * (카카오톡 공유하기 -> 수정필요)
     */
    @Transactional
    public User updateUserInfo(UserUpdateDto dto) {
        User user = userRepository.findByUserSeq(dto.getUserSeq());
        user.setCoupleYn("Y");
        user.setCoupleId(dto.getCoupleId());
        userRepository.save(user);
        return user;
    }

    /**
     * Redis를 이용한 로그아웃
     */
    @Transactional
    public void logout(Long userSeq) {
        String redisUserSeq = userSeq.toString();
        redisTemplate.delete(redisUserSeq); //redis에서 refresh 토큰 삭제
        System.out.println("로그아웃 완료, refresh토큰 삭제: "+userSeq);
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

    /**
     * 커플 연결 요청

     public void makeCouple(Long userSeq, Long coupleUserSeq) {

     }
     */

    /**
     * 커플 연결 수락
     */
    public void acceptCouple(Long userSeq, Long coupleUserSeq, Long coupleId) {
        User user = userRepository.findByUserSeq(userSeq);
        User coupleUser = userRepository.findByUserSeq(coupleUserSeq);

        user.setCoupleId(coupleId);
        userRepository.save(user);
        coupleUser.setCoupleId(coupleId);
        userRepository.save(coupleUser);
        System.out.println("커플 연결 수락");
    }

    /**
     * 커플 연결 끊기
     */
    @Transactional
    public void disconnectCouple(Long coupleId) {
        List<User> couple = userRepository.findByCoupleId(coupleId);
        couple.get(0).setCoupleYn("N");
        couple.get(0).setCoupleId(null);
        couple.get(1).setCoupleYn("N");
        couple.get(1).setCoupleId(null);
        userRepository.save(couple.get(0));
        userRepository.save(couple.get(1));
        System.out.println("두 유저의 커플 id 삭제");
    }

    /**
     * 사용자 유형 등록
     */
    public User saveTypes(TypeRequestDto dto) {
        User user = userRepository.findByUserSeq(dto.getUserSeq());
        user.setType1(dto.getType1());
        user.setType2(dto.getType2());
        return userRepository.save(user);
    }
}
