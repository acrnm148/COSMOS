package com.cosmos.back.controller;

import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.JwtToken;
import com.cosmos.back.dto.user.UserProfileDto;
import com.cosmos.back.dto.user.UserUpdateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        return "jenkins Test";
    }

    /**
     * 회원 조회
     */
    @Operation(summary = "유저 프로필 정보를 가져옴", description = "유저 프로필 정보를 가져옴")
    @GetMapping("/accounts/userInfo/{userSeq}")
    public ResponseEntity<?> getUserProfile(@PathVariable("userSeq")Long userSeq, @RequestHeader(value = "Authorization") String token) {
        //userSeq 일치, access토큰 유효 여부 체크
        token = token.substring(7);
        JwtState state = jwtService.checkUserSeqWithAccess(userSeq, token);
        if (state.equals(JwtState.MISMATCH_USER)) { //userSeq 불일치
            return ResponseEntity.ok().body(jwtService.mismatchUserResponse());
        } else if (state.equals(JwtState.EXPIRED_ACCESS)) { //access 만료
            return ResponseEntity.ok().body(jwtService.requiredRefreshTokenResponse());
        }

        try {
            System.out.println("유저 조회 token 체크 : "+token);
            UserProfileDto userProfileDto = userService.getUser(userSeq);
            return ResponseEntity.ok().body(userProfileDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    /**
     * 회원 정보 수정
     */
    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정")
    @PutMapping("/accounts/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserUpdateDto userUpdateDto,
                                            @RequestHeader(value = "Authorization") String token) {
        //userSeq 일치, access토큰 유효 여부 체크
        token = token.substring(7);
        JwtState state = jwtService.checkUserSeqWithAccess(userUpdateDto.getUserSeq(), token);
        if (state.equals(JwtState.MISMATCH_USER)) { //userSeq 불일치
            return ResponseEntity.ok().body(jwtService.mismatchUserResponse());
        } else if (state.equals(JwtState.EXPIRED_ACCESS)) { //access 만료
            return ResponseEntity.ok().body(jwtService.requiredRefreshTokenResponse());
        }

        try {
            User updatedUser = userService.updateUserInfo(userUpdateDto);
            if (updatedUser != null) {
                System.out.println("수정되었습니다.");
            } else {
                System.out.println("수정에 실패했습니다.");
            }
            return ResponseEntity.ok().body(updatedUser);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정에 실패했습니다.");
        }
    }

    /**
     * 로그아웃
     */
    @Operation(summary = "로그아웃", description = "로그아웃")
    @GetMapping("/accounts/logout/{userSeq}")
    public ResponseEntity<?> logout(@PathVariable("userSeq")Long userSeq, @RequestHeader("Authorization") String token) {

        //userSeq 일치, access토큰 유효 여부 체크
        token = token.substring(7);
        JwtState state = jwtService.checkUserSeqWithAccess(userSeq, token);
        if (state.equals(JwtState.MISMATCH_USER)) { //userSeq 불일치
            return ResponseEntity.ok().body(jwtService.mismatchUserResponse());
        } else if (state.equals(JwtState.EXPIRED_ACCESS)) { //access 만료
            return ResponseEntity.ok().body(jwtService.requiredRefreshTokenResponse());
        }

        userService.logout(userSeq);
        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }

    /**
     *   JWT를 이용한 카카오 로그인
     */
    /**front-end로 부터 받은 인가 코드 받기 및 사용자 정보 받기,회원가입 */
    @Operation(summary = "kakao 로그인", description = "kakao 로그인")
    @GetMapping("/accounts/auth/login/kakao")
    public Map<String,String> KakaoLogin(@RequestParam("code") String code) {
        //access 토큰 받기
        KakaoToken oauthToken = kakaoService.getAccessToken(code);
        //사용자 정보받기 및 회원가입
        User saveUser = kakaoService.saveUser(oauthToken.getAccess_token());
        //jwt토큰 Redis에 저장
        JwtToken jwtTokenDTO = jwtService.getJwtToken(saveUser.getUserSeq());

        return jwtService.successLoginResponse(jwtTokenDTO, saveUser.getUserSeq(), saveUser.getCoupleId());
    }
    //직접 인가 코드 받기
    @GetMapping("/login/oauth2/code/kakao")
    @Operation(summary = "kakao 코드 발급", description = "kakao 코드 발급")
    public String KakaoCode(@RequestParam("code") String code) {
        return code;
    }

    /**
     * access 토큰 재발급 요청
     * access 토큰 요청 왔을 때 > access 토큰 유효기간 만료되면 > 응답 안함
     * access 토큰 재발급 요청 > refresh 토큰 유효기간을 체크
     *  - refresh 토큰이 유효하면 > access 토큰만 재발급해줬고
     *  - refresh 토큰이 유효하지 않으면 > 내부에서 refresh 토큰을 재발급해주면 된다. 프론트엔드에 줄 필요가 없다.
     */
    @Operation(summary = "access토큰 재발급 후 프론트에 전달", description = "access토큰 재발급 후 프론트에 전달")
    @Parameter(description = "userSeq를 파라미터로 받습니다.")
    @GetMapping("/access/{userSeq}")
    public Map<String,String> refreshToken(@PathVariable("userSeq") Long userSeq,
                                           @RequestHeader(value="Authorization") String token,
                                           HttpServletResponse response) throws JsonProcessingException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        token = token.substring(7);
        JwtToken jwtToken = jwtService.validRefreshToken(userSeq);
        Map<String, String> jsonResponse = jwtService.recreateTokenResponse(jwtToken);

        return jsonResponse;
    }

    /**
     * 커플 연결 수락 - 카카오 공유하기에서 수락
     */
    @Operation(summary = "커플 연결 수락", description = "커플 연결 수락")
    @PostMapping("/couples/accept/{coupleId}")
    public ResponseEntity<Long> acceptCouple(@PathVariable("coupleId") Long code,
            @RequestBody Map<String, Long> map) {
        Long userSeq = map.get("userSeq");
        Long coupleUserSeq = map.get("coupleUserSeq");

        Long coupleId = userService.acceptCouple(userSeq, coupleUserSeq, code);
        if (coupleId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(coupleId, HttpStatus.OK);
    }

    /**
     * 커플 연결 끊기
     */
    @Operation(summary = "커플 연결 끊기", description = "커플 연결 끊기")
    @DeleteMapping("/couples/{coupleId}")
    public ResponseEntity<Long> disconnectCouple(@PathVariable("coupleId") Long coupleId) {
        userService.disconnectCouple(coupleId);
        return new ResponseEntity<>(coupleId, HttpStatus.OK);
    }

    /**
     * 사용자 유형 등록
     */
    @Operation(summary = "사용자 유형 등록", description = "사용자 유형 등록")
    @PostMapping("/couples/type")
    public ResponseEntity<Long> saveTypes(@RequestBody Map<String, Object> map) {
        Long userSeq = Long.valueOf((Integer) map.get("userSeq"));
        String type1 = map.get("type1").toString();
        String type2 = map.get("type2").toString();
        User user = userService.saveTypes(userSeq, type1, type2);
        return new ResponseEntity<>(userSeq, HttpStatus.OK);
    }

    /**
     * 난수 생성 후 리턴
     */
    @Operation(summary = "난수 생성 후 리턴", description = "난수 생성 후 리턴")
    @GetMapping("/makeCoupleId")
    public ResponseEntity<Long> makeCoupleId() {
        Long code = userService.makeCoupleId();
        return new ResponseEntity<>(code, HttpStatus.OK);
    }
}
