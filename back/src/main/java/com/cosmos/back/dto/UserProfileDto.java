package com.cosmos.back.dto;

import com.cosmos.back.model.Review;
import com.cosmos.back.model.UserCourse;
import com.cosmos.back.model.UserPlace;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String type1;
    private String type2;
    private Long coupleUserId;
    private LocalDateTime createTime;

    // 유저 - (유저 - 데이트 코스)
    List<UserCourse> userCourses = new ArrayList<>();

    // 유저 - 리뷰
    List<Review> reviews = new ArrayList<>();

    // 유저 - (유저 - 장소)
    List<UserPlace> userPlaces = new ArrayList<>();


}
