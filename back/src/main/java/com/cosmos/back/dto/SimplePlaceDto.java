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
    private Long placeId; // id
    private String placeName; // 이름
    private String latitude; // 위도
    private String longitude; // 경도
    private String thumbNailUrl; // 썸네일
    private String address; // 주소
    private Double score; // 별점
    private String overview; // 개요
    private Integer orders; // 순서
    private String phoneNumber; // 전화번호


    // 별점을 제외한 생성자
    public SimplePlaceDto (Long placeId, String placeName, String latitude, String longitude, String thumbNailUrl, String address, String overview, Integer orders, String phoneNumber) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.thumbNailUrl = thumbNailUrl;
        this.address = address;
        this.overview = overview;
        this.orders = orders;
        this.phoneNumber = phoneNumber;
    }
}