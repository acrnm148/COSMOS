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

    private String experience; // 체험안내

    @Column(name = "parking_yn")
    private String parkingYn; // 주차 사용 가능 여부

    @Column(name = "pet_yn")
    private String petYn; // 애완동물 동반 가능 여부
}
