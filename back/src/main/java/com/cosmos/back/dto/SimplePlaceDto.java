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
}