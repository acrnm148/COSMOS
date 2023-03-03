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
@DiscriminatorValue("tour")
@Table(name = "tour")
public class Tour extends Place {

    @Column(name = "day_off")
    private String dayOff; // 쉬는날

    private String experience; // 체험안내

    @Column(name = "possible_date")
    private String possibleDate; // 이용시기

    @Column(name = "stroller_yn")
    private String strollerYn; // 유모차 사용 가능 여부

    @Column(name = "pet_yn")
    private String petYn; // 애완동물 동반 가능 여부
}
