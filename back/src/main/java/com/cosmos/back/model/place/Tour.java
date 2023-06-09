package com.cosmos.back.model.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("tour")
@Table(name = "tour")
@Getter
@SuperBuilder
public class Tour extends Place {

    @Column(name = "pet_yn")
    private String petYn; // 애완동물 동반 가능 여부

    @Column(name = "introduce")
    private String introduce; // 개요

    @Column(name = "inside_yn")
    private String insideYn; // 실내, 실외

    @Column(name = "day_off")
    private String dayOff; // 쉬는 날

    private String program; // 투어 프로그램
}
