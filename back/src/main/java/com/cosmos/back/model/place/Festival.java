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
@DiscriminatorValue("festival")
@Table(name = "festival")
public class Festival extends Place {

    @Column(name = "start_date")
    private String startDate; // 행사 시작일

    @Column(name = "end_date")
    private String endDate; // 행사 종료일

    private String location; // 행사 장소

    private String price; // 이용 요금

    @Column(name = "taken_time")
    private String takenTime; // 관람 소요 시간

    @Column(name = "possible_age")
    private String possibleAge; // 관람 가능 연령
}
