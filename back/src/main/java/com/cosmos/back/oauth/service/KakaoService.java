package com.cosmos.back.oauth.service;

import com.cosmos.back.oauth.provider.Token.KakaoToken;
import com.cosmos.back.oauth.provider.profile.KakaoProfile;
import com.cosmos.back.repository.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cosmos.back.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoService {

    private final UserRepository userRepository;

    private final String client_id = "097d883a03c0da953d919d990701da5f";
    private final String client_secret = "af5un2n5wi857RPKyB7wBFPKhjBBebd4";
    // localhost
    private final String redirect_uri = "http://localhost:8081/api/login/oauth2/code/kakao";
    // develop
//    private final String redirect_uri = "http://j8e104.p.ssafy.io:8081/api/login/oauth2/code/kakao";
    // deploy
//    private final String redirect_uri = "https://j8e104.p.ssafy.io/api/login/oauth2/code/kakao";
    private final String accessTokenUri = "https://kauth.kakao.com/oauth/token";
    private final String UserInfoUri = "https://kapi.kakao.com/v2/user/me";

    /**
     * 카카오로 부터 엑세스 토큰을 받는 함수
     */
    public KakaoToken getAccessToken(String code) {

        //요청 param (body)
        MultiValueMap<String , String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id",client_id );
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);
        params.add("client_secret", client_secret);
        params.add("scope", "age_range,birthday");

        //request
        WebClient wc = WebClient.create(accessTokenUri);
        String response = wc.post()
                .uri(accessTokenUri)
                .body(BodyInserters.fromFormData(params))
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" ) //요청 헤더
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("response:" + response);

        //json형태로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoToken kakaoToken =null;

        try {
            kakaoToken = objectMapper.readValue(response, KakaoToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoToken;
    }

    /**
     * 사용자 정보 가져오기
     */
    public KakaoProfile findProfile(String token) {

        //Http 요청
        WebClient wc = WebClient.create(UserInfoUri);
        String response = wc.post()
                .uri(UserInfoUri)
                .header("Authorization", "Bearer " + token)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response, KakaoProfile.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("===========KakaoProfile=================");
        System.out.println(kakaoProfile);

        return kakaoProfile;
    }

    /**
     * 카카오 로그인 사용자 강제 회원가입
     */
    @Transactional
    public User saveUser(String access_token) {
        KakaoProfile profile = findProfile(access_token); //사용자 정보 받아오기
        User user = userRepository.findByUserId(profile.getId()); //쿼리1
        System.out.println("유저 저장:"+profile);

        //처음이용자 강제 회원가입
        if(user ==null) {
            //연령대 저장
            String newAgeRange = "";
            String oldAgeRange = profile.getKakao_account().getAge_range();
            switch (oldAgeRange) {
                case "1~9": newAgeRange += "10대 미만"; break;
                case "10~14": newAgeRange += "10대 초반"; break;
                case "15~19": newAgeRange += "10대 후반"; break;
                case "20~29": newAgeRange += "20"; break;
                case "30~39": newAgeRange += "30"; break;
                case "40~49": newAgeRange += "40"; break;
                case "50~59": newAgeRange += "50"; break;
                case "60~69": newAgeRange += "60"; break;
                case "70~79": newAgeRange += "70"; break;
                case "80~89": newAgeRange += "80"; break;
                case "90~": newAgeRange += "90"; break;
            }

            user = User.builder()
                    .userId(profile.getId())
                    //.userName(profile.getKakao_account().getProfile().getName()) //대부분 name 설정 X
                    .userName(profile.getKakao_account().getProfile().getNickname())
                    //.phoneNumber(profile.getKakao_account().getPhone_number()) //접근권한 X,직접 입력 해야함
                    .profileImgUrl(profile.getKakao_account().getProfile().getProfile_image_url())
                    .ageRange(newAgeRange)
                    .birthday(profile.getKakao_account().getBirthday())
                    .email(profile.getKakao_account().getEmail())
                    .coupleYn("N")
                    .role("USER") //일단 유저로 넣음.
                    .createTime(LocalDateTime.now())
                    .build();

            userRepository.save(user);  //쿼리2
        }
        return user;
    }
}
