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
@DiscriminatorValue("restaurant")
@Table(name = "restaurant")
@Getter
@SuperBuilder
public class Restaurant extends Place {

    private String playground; // 놀이방

    @Column(name = "day_off")
    private String dayOff; // 쉬는날

    @Column(name = "representative_menu")
    private String representativeMenu; // 대표 메뉴

    @Column(name = "total_menu")
    private String totalMenu; // 취급 메뉴

    @Column(name = "smoking_yn")
    private String smokingYn; // 흡연 가능 여부

    @Column(name = "card_yn")
    private String cardYn; // 신용카드 가능 여부

    @Column(name = "takeout_yn")
    private String takeoutYn; // 포장 가능 여부

    @Column(name = "reserve_info")
    private String reserveInfo; // 예약안내

    @Column(name = "open_time")
    private String openTime; // 여는시간
}
