package com.cosmos.back.dto.response.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourResponseDto {
    private Long placeId;
    private String name;
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private String thumbNailUrl;
    private String detail;
    private String parkingYn;
    private String address;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String type;
    private String petYn;
    private String introduce;
    private String insideYn;
    private String dayOff;
    private String program;
}