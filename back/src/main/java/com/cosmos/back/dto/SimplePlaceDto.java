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
    private Long placeId;
    private String placeName;
    private String phoneNumber; // 연락처
    private String latitude; // 위도
    private String longitude; // 경도
    private String thumbNailUrl; // 썸네일
    private String address; // 주소
}