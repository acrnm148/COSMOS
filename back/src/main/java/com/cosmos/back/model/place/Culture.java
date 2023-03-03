package com.cosmos.back.model.place;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("culture")
@Table(name = "culture")
public class Culture extends Place {

    @Column(name = "day_off")
    private String dayOff; // 쉬는날

    private String price; // 요금

    private String discount; // 할인 정보

    @Column(name = "taken_time")
    private String takenTime; // 관람 소요 시간

    @Column(name = "parking_fee")
    private String parkingFee; // 주차 요금

    @Column(name = "stroller_yn")
    private String strollerYn; // 유모차 사용 가능 여부

    @Column(name = "pet_yn")
    private String petYn; // 애완동물 동반 가능 여부

    @Column(name = "card_yn")
    private String cardYn; // 신용카드 사용 가능 여부
}
