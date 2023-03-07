package com.cosmos.back.model.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "place")
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

//    private String introduce; // 개요

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
}
