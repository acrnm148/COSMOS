package com.cosmos.back.model.place;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("leisure")
@Table(name = "leisure")
@Getter
public class Leisure extends Place{

    @Column(name = "acceptable_people")
    private String acceptablePeople; // 수용 인원

    @Column(name = "day_off")
    private String dayOff; // 쉬는 날

    @Column(name = "parking_cost")
    private String parkingCost; // 주차 요금

    @Column(name = "open_time")
    private String openTime; // 운영 시간

    @Column(name = "open_period")
    private String openPeriod; // 운영 계절

    private String price; // 이용 가격

    @Column(name = "age_range")
    private String ageRange; // 나이 제한

    @Column(name = "pet_yn")
    private String petYn; // 애완동물 동반 가능 여부
}
