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
@DiscriminatorValue("shopping")
@Table(name = "shopping")
@Getter
@SuperBuilder
public class Shopping extends Place {

    @Column(name = "shopping_list")
    private String shoppingList; // 판매 품목

    @Column(name = "day_off")
    private String dayOff; // 쉬는날

    @Column(name = "stroller_yn")
    private String strollerYn; // 유모차 사용 가능 여부

    @Column(name = "pet_yn")
    private String petYn; // 애완동물 동반 가능 여부

    @Column(name = "card_yn")
    private String cardYn; // 신용카드 사용 가능 여부

    @Column(name = "open_time")
    private String openTime; // 여는 시간

    @Column(name = "open_day")
    private String openDay; // 여는 날
}
