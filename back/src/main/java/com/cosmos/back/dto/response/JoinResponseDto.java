package com.cosmos.back.dto.response;

import com.cosmos.back.auth.jwt.refreshToken.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinResponseDto {
    private String id;
    private String userid; //일반사용자-입력한 아이디, 카카오 사용자-카카오 고유 id(provider id)
    private String password;
    private String roles; //USER,ADMIN 게 넣을것이다.

    private String provider;
    private String nickname;
    private String profileImg;
    private String username;
    private String birth;
    private String email;
    private String tel;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String birthYear;
    private String gender;

    private Boolean emailAuth; //이메일인증 - 인증링크 클릭 시, 권한 상태 업데이트
    private String authToken;

    private LocalDateTime createTime;
    private RefreshToken jwtRefreshToken;
}
