package com.cosmos.back.model.place;


import com.cosmos.back.model.CoursePlace;
import com.cosmos.back.model.ReviewPlace;
import com.cosmos.back.model.UserPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "place", indexes = @Index(name = "type", columnList = "type"))
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@SuperBuilder
public class Place {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "place_id")
    private Long id; // 장소 ID

    private String name; // 장소 이름

    @Column(name = "phone_number")
    private String phoneNumber; // 연락처

    private String latitude; // 위도

    private String longitude; // 경도

    @Column(name = "thumb_nail_url")
    private String thumbNailUrl; // 썸네일

    private String detail; // 상세정보

    private String parkingYn; // 주차 가능 여부

    private String address; // 주소

    private String img1; // 이미지1

    private String img2; // 이미지2

    private String img3; // 이미지3

    private String img4; // 이미지4

    private String img5; // 이미지5

    private String type; // 타입

    private String tendency; // 장소 선호도

    // 장소 - (유저 - 장소)
    @OneToMany(mappedBy = "place")
    List<UserPlace> userPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    List<ReviewPlace> reviewPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    List<CoursePlace> coursePlaces = new ArrayList<>();
}
