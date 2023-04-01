package com.cosmos.back.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SimplePlaceDto {
    private Long courseId; // id
    private Long placeId; // id
    private String name; // 이름
    private String latitude; // 위도
    private String longitude; // 경도
    private String thumbNailUrl; // 썸네일
    private String address; // 주소
    private Double score; // 별점
    private String detail; // 개요
    private Integer orders; // 순서
    private String phoneNumber; // 전화번호
    private String type; // 타입


    // 별점을 제외한 생성자
    public SimplePlaceDto(Long courseId, Long placeId, String name, String latitude, String longitude, String thumbNailUrl, String address, String detail, Integer orders, String phoneNumber, String type) {
        this.courseId = courseId;
        this.placeId = placeId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.thumbNailUrl = thumbNailUrl;
        this.address = address;
        this.detail = detail;
        this.orders = orders;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }
}