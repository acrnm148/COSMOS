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
@DiscriminatorValue("restaurant")
@Table(name = "restaurant")
public class Restaurant extends Place {

    private String playground; // 놀이방

    @Column(name = "day_off")
    private String dayOff; // 쉬는날

    private String representativeMenu; // 대표 메뉴

    private String totalMenu; // 취급 메뉴

    @Column(name = "cigarette_yn")
    private String cigaretteYn; // 흡연 가능 여부

    @Column(name = "card_yn")
    private String cardYn; // 신용카드 가능 여부

    @Column(name = "takeout_yn")
    private String takeoutYn; // 포장 가능 여부

    @Column(name = "reserve_info")
    private String reserveInfo; // 예약안내
}
