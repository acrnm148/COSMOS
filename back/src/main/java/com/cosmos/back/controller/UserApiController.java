package com.cosmos.back.controller;

import com.cosmos.back.auth.jwt.JwtToken;
import com.cosmos.back.dto.UserProfileDto;
import com.cosmos.back.dto.UserUpdateDto;
import com.cosmos.back.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.cosmos.back.auth.jwt.service.JwtProviderService;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.service.UserService;
import com.cosmos.back.model.User;
import com.cosmos.back.oauth.provider.Token.KakaoToken;
import com.cosmos.back.oauth.service.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Tag(name = "user", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {

    private final JwtService jwtService;
    private final UserService userService;
    private final KakaoService kakaoService;

    @Operation(summary = "서버 테스트", description = "서버 테스트")
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * 회원 조회
     */
    @Operation(summary = "유저 프로필 정보를 가져옴", description = "유저 프로필 정보를 가져옴")
    @GetMapping("/account/userInfo/{userSeq}")
    public ResponseEntity<?> getUserProfile(@PathVariable("userSeq")Long userSeq, @RequestHeader(value = "Authorization") String token) {
        try {
            System.out.println("유저 조회 token 체크 : "+token);
            UserProfileDto userProfileDto = userService.getUser(userSeq, token.substring(7));
            return ResponseEntity.ok().body(userProfileDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    /**
     * 회원 정보 수정
     */
    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정")
    @PostMapping("/account/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserUpdateDto userUpdateDto) { //@RequestHeader(value = "Authorization") String token) {
        try {
            User updatedUser = userService.updateUserInfo(userUpdateDto);
            if (updatedUser != null) {
                System.out.println("수정되었습니다.");
            } else {
                System.out.println("수정에 실패했습니다.");
            }
            return ResponseEntity.ok().body(updatedUser);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정에 실패했습니다..");
        }
    }

    /**
     * 로그아웃
     */
    @Operation(summary = "로그아웃", description = "로그아웃")
    @GetMapping("/account/logout/{userSeq}")
    public ResponseEntity<?> logout(@PathVariable("userSeq")String userSeq, @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        boolean res = userService.logout(Long.parseLong(userSeq), token);
        if (res) {
            System.out.println("로그아웃 완료");
            return ResponseEntity.ok().body("로그아웃 되었습니다.");
        }
        System.out.println("로그아웃 실패");
        return ResponseEntity.ok().body("로그아웃에 실패했습니다.");
    }

    /**
     *   JWT를 이용한 카카오 로그인
     */
    /**front-end로 부터 받은 인가 코드 받기 및 사용자 정보 받기,회원가입 */
    @Operation(summary = "kakao 로그인", description = "kakao 로그인")
    @GetMapping("/account/auth/login/kakao")
    public Map<String,String> KakaoLogin(@RequestParam("code") String code) {
        //access 토큰 받기
        KakaoToken oauthToken = kakaoService.getAccessToken(code);
        //사용자 정보받기 및 회원가입
        User saveUser = kakaoService.saveUser(oauthToken.getAccess_token());
        //jwt토큰 DB에 저장
        JwtToken jwtTokenDTO = jwtService.getJwtToken(saveUser.getUserId());

        return jwtService.successLoginResponse(jwtTokenDTO);
    }
    //test로 직접 인가 코드 받기
    @GetMapping("/login/oauth2/code/kakao")
    @Operation(summary = "kakao 코드 발급", description = "kakao 코드 발급")
    public String KakaoCode(@RequestParam("code") String code) {
        return "카카오 로그인 인증완료, code: "  + code;
    }

    /**
     * refresh token 재발급
     */
    @Operation(summary = "refreshToken 재발급", description = "refreshToken 재발급")
    @Parameter(description = "userId를 파라미터로 받습니다.")
    @GetMapping("/refresh/{userId}")
    public Map<String,String> refreshToken(@PathVariable("userId") String userId, @RequestHeader("refreshToken") String refreshToken,
                                           HttpServletResponse response) throws JsonProcessingException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        JwtToken jwtToken = jwtService.validRefreshToken(userId, refreshToken);
        Map<String, String> jsonResponse = jwtService.recreateTokenResponse(jwtToken);

        return jsonResponse;
    }

}
