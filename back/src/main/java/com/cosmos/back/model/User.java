package com.cosmos.back.model;

import com.cosmos.back.dto.request.NotificationRequestDto;
import lombok.*;
import org.springframework.context.ApplicationEventPublisher;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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
    private Long coupleUserSeq;
    private String coupleProfileImgUrl;
    private String coupleSuccessDate;

    //private String provider;

    private String type1;
    private String type2;
    @Column(name = "couple_id")
    private Long coupleId;
    @Column(name = "create_time")
    private LocalDateTime createTime;

    // 유저 - 리뷰
    @OneToMany(mappedBy = "user")
    List<Review> reviews = new ArrayList<>();

    // 유저 - (유저 - 장소)
    @OneToMany(mappedBy = "user")
    List<UserPlace> userPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Course> courses = new ArrayList<>();
}
