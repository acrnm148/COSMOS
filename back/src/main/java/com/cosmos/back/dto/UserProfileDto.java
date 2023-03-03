package com.cosmos.back.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {

    private Long userSeq;

    private String userId; //일반사용자-입력한 아이디, 카카오 사용자-카카오 고유 id(provider id)
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

    private String refreshToken;

    private LocalDateTime createTime;


}
