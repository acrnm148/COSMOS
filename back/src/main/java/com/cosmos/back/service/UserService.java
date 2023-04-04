package com.cosmos.back.service;

import com.cosmos.back.annotation.Generated;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.user.UserProfileDto;
import com.cosmos.back.dto.user.UserUpdateDto;
import com.cosmos.back.model.NotificationType;
import com.cosmos.back.repository.user.UserRepository;
import com.cosmos.back.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 실제 JWT 토큰과 관련된 서비스
 * refresh 토큰을 검사 -> 유효하면 access
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final ApplicationEventPublisher eventPublisher;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;
    private final NotificationService notificationService;

    /**
     * 유저 정보 조회
     * accessToken을 복호화(디코딩)해서 유저 정보 가져옴
     */
    @Transactional
    public UserProfileDto getUser(Long userSeq) {
        System.out.println("getUser 함수 진입");
        try {
            User user = userRepository.findByUserSeq(userSeq);

            Long coupleDay = null;
            if (user.getCoupleSuccessDate() != null) {
                String coupleSuccessDate = user.getCoupleSuccessDate();
                LocalDate date = LocalDate.parse(coupleSuccessDate, DateTimeFormatter.ISO_DATE);
                LocalDate nowDate = LocalDate.now();
                coupleDay = ChronoUnit.DAYS.between(date, nowDate);
                System.out.println("며칠차:"+ coupleDay);
            }

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
                    .coupleId(user.getCoupleId())
                    .coupleUserId(user.getCoupleId())
                    .createTime(LocalDateTime.now())
                    .coupleProfileImgUrl(user.getCoupleProfileImgUrl())
                    .coupleSuccessDate(user.getCoupleSuccessDate())
                    .coupleDay(coupleDay+1)
                    .build();

            System.out.println("유저 프로필 : "+userProfileDto);
            return userProfileDto;
        }catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * 유저 정보 수정
     */
    @Transactional
    public User updateUserInfo(UserUpdateDto dto) {
        User user = userRepository.findByUserSeq(dto.getUserSeq());
        user.setPhoneNumber(dto.getPhoneNumber());
        userRepository.save(user);
        return user;
    }

    /**
     * Redis를 이용한 로그아웃
     */
    @Generated
    @Transactional
    public void logout(Long userSeq) {
        String redisUserSeq = userSeq.toString();
        redisTemplate.delete(redisUserSeq); //redis에서 refresh 토큰 삭제
        System.out.println("로그아웃 완료, refresh토큰 삭제: "+userSeq);
    }

    /**
     * 커플 연결 수락
     */
    @Transactional
    public Long acceptCouple(Long userSeq, Long coupleUserSeq, Long code) {
        User user = userRepository.findByUserSeq(userSeq);
        User coupleUser = userRepository.findByUserSeq(coupleUserSeq);

        System.out.println("user: "+user);
        System.out.println("coupleUser: "+coupleUser);

        if (coupleUser == null) {
            System.out.println("커플유저가 존재하지 않습니다.");
            return null;
        }
        if (!(user.getCoupleId()==null && coupleUser.getCoupleId()==null)) {
            System.out.println("이미 커플인 유저입니다.");
            return null;
        }

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = format.format(date);
        System.out.println("현재날짜:["+nowDate+"]");

        user.setCoupleId(code);
        user.setCoupleYn("Y");
        user.setCoupleUserSeq(coupleUserSeq);
        user.setCoupleProfileImgUrl(coupleUser.getProfileImgUrl());
        user.setCoupleSuccessDate(nowDate);
        coupleUser.setCoupleId(code);
        coupleUser.setCoupleYn("Y");
        coupleUser.setCoupleUserSeq(userSeq);
        coupleUser.setCoupleProfileImgUrl(user.getProfileImgUrl());
        coupleUser.setCoupleSuccessDate(nowDate);
        userRepository.save(user);
        userRepository.save(coupleUser);

        System.out.println("user: "+user);
        System.out.println("coupleUser: "+coupleUser);
        System.out.println("커플 연결 수락, 커플아이디:"+code);

        // 알림 전송
        notificationService.send(userSeq, NotificationType.MESSAGE, "커플 요청이 수락되었습니다.", "");

        return code;
    }

    /**
     * 커플 연결 끊기
     */
    @Transactional
    public void disconnectCouple(Long coupleId) {
        List<User> couple = userRepository.findByCoupleId(coupleId);
        if (couple.size()==0) {
            System.out.println(coupleId+"에 해당하는 커플이 없습니다.");
            return;
        }
        couple.get(0).setCoupleYn("N");
        couple.get(0).setCoupleProfileImgUrl(null);
        couple.get(0).setCoupleId(null);
        couple.get(0).setCoupleUserSeq(null);
        couple.get(0).setCoupleSuccessDate(null);
        couple.get(1).setCoupleYn("N");
        couple.get(1).setCoupleProfileImgUrl(null);
        couple.get(1).setCoupleId(null);
        couple.get(1).setCoupleUserSeq(null);
        couple.get(1).setCoupleSuccessDate(null);
        userRepository.save(couple.get(0));
        userRepository.save(couple.get(1));
        System.out.println("커플 연결이 끊어졌습니다.");
    }

    /**
     * 사용자 유형 등록
     */
    @Transactional
    public User saveTypes(Long userSeq, String type1, String type2) {
        User user = userRepository.findByUserSeq(userSeq);
        user.setType1(type1);
        user.setType2(type2);
        System.out.println("유형 저장 완료");
        return userRepository.save(user);
    }

    /**
     * 커플아이디 난수 생성
     */
    @Generated
    public Long makeCoupleId() {
        Random random = new Random(); // 랜덤 객체 생성
        Long coupleId = Long.valueOf(random.nextInt(1000000000));//10자리 미만의 난수 반환
        System.out.println("생성된 커플아이디: " +coupleId);
        // 알림 전송
//        notificationService.send(1L, NotificationType.MESSAGE, "커플 요청이 수락되었습니다.", "");


        return coupleId;
    }
}
