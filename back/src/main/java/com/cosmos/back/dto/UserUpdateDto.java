package com.cosmos.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
    private String userId; //수정불가
    private String nickname;
    private String tel;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String nowPassword;
    private String newPassword;
    private String profileImg;
}
