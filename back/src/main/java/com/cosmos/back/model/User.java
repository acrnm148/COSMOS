package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cosmos.back.auth.jwt.refreshToken.RefreshToken;
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

    private String userId; //일반사용자-입력한 아이디, 카카오 사용자-카카오 고유 id(provider id)
    private String password;
    private String role; //USER,ADMIN

    private String provider;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "user_name")
    private String userName;
    private String birth;
    private String email;
    private String tel;
    private String addr1;
    private String addr2;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "birth_year")
    private String birthYear;
    private String gender;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "refreshToken_id")
    private RefreshToken jwtRefreshToken;

    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;

    /**
     *  refresh 생성자, setter
     */
    public void createRefreshToken(RefreshToken refreshToken) {
        this.jwtRefreshToken = refreshToken;
    }
    public void SetRefreshToken(String refreshToken) {
        System.out.println("로그인 후 set refresh token 진입:"+ refreshToken + " "+this.jwtRefreshToken);
        this.jwtRefreshToken.setRefreshToken(refreshToken);
    }
    public void updateRefreshToken(RefreshToken refreshToken) {
        this.jwtRefreshToken = refreshToken;
    }

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
