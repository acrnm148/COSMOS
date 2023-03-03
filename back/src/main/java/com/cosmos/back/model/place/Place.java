package com.cosmos.back.model.place;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "place")
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Place {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "place_id")
    private Long id; // 장소 ID

    private String name; // 장소 이름

    @Column(name = "phone_number")
    private String phoneNumber; // 연락처

    private String latitude; // 위도

    private String longitude; // 경도

    private String introduce; // 개요

    @Column(name = "thumb_nail_url")
    private String thumbNailUrl; // 썸네일

    private String detail; // 상세정보

    private String parking; // 주차시설

    private String address; // 주소
}
