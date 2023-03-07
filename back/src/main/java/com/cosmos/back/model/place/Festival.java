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

    private String price; // 이용 요금

    @Column(name = "taken_time")
    private String takenTime; // 관람 소요 시간

    @Column(name = "introduce")
    private String introduce; // 개요
}
