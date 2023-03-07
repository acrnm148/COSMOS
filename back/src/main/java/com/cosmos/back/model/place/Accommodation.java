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
@DiscriminatorValue("accommodation")
@Table(name = "accommodation")
public class Accommodation extends Place {

    @Column(name = "acceptable_people")
    private String acceptablePeople; // 수용 가능

    @Column(name = "room_num")
    private String roomNum; // 객실 수

    @Column(name = "room_type")
    private String roomType; // 객실 유형

    @Column(name = "cook_yn")
    private String cookYn; // 조리 가능 여부

    @Column(name = "check_in")
    private String checkIn; // 체크인 시간

    @Column(name = "check_out")
    private String checkOut; // 체크아웃 시간

    @Column(name = "reservation_page")
    private String reservationPage; // 예약안내 홈페이지

    @Column(name = "pickup_yn")
    private String pickupYn; // 픽업 서비스 가능 여부

    @Column(name = "karaoke_yn")
    private String karaokeYn; // 노래방 존재 여부

    @Column(name = "bbq_yn")
    private String bbqYn; // 바베큐 가능 여부

    @Column(name = "gym_yn")
    private String gymYn; // 헬스장 존재 여부

    private String refund; // 환불 규정
}
