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
@DiscriminatorValue("culture")
@Table(name = "culture")
@Getter
@SuperBuilder
public class Culture extends Place {

    @Column(name = "day_off")
    private String dayOff; // 쉬는날

    @Column(name = "pet_yn")
    private String petYn; // 애완동물 동반 가능 여부
}
