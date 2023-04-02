package com.cosmos.back.dto.user;

import com.cosmos.back.model.Review;
import com.cosmos.back.model.UserPlace;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {

    private Long userSeq;
    private String userId;
    private String userName;
    private String phoneNumber;
    private String profileImgUrl;
    private String coupleYn;
    private String ageRange;
    private String email;
    private String birthday;
    private String role; //USER,ADMIN
    private String coupleProfileImgUrl; //커플의 프로필 사진
    private String coupleSuccessDate;
    private Long coupleDay;

    private String type1;
    private String type2;
    private Long coupleUserId;
    private Long coupleId;
    private LocalDateTime createTime;

    private List<Review> reviews = new ArrayList<>();//유저-리뷰
    private List<UserPlace> userPlaces = new ArrayList<>();//유저-장소


}
