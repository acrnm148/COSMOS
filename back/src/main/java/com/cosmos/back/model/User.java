package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="user_seq")
    private Long userSeq;
    @Column(name ="user_id")
    private String userId; //일반사용자-입력한 아이디, 카카오 사용자-카카오 고유 id(provider id)
    @Column(name = "user_name")
    private String userName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "profile_img_url")
    private String profileImgUrl;
    @Column(name = "couple_yn")
    private String coupleYn;
    @Column(name = "age_range")
    private String ageRange;
    private String email;
    private String birthday;
    private String role; //USER,ADMIN

    //private String provider;

    private String type1;
    private String type2;
    @Column(name = "couple_id")
    private Long coupleId;
    @Column(name = "create_time")
    private LocalDateTime createTime;

    // 유저 - (유저 - 데이트 코스)
    @OneToMany(mappedBy = "user")
    List<UserCourse> userCourses = new ArrayList<>();

    // 유저 - 리뷰
    @OneToMany(mappedBy = "user")
    List<Review> reviews = new ArrayList<>();

    // 유저 - (유저 - 장소)
    @OneToMany(mappedBy = "user")
    List<UserPlace> userPlaces = new ArrayList<>();
}
