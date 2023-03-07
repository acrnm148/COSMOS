package com.cosmos.back.dto.response.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponseDto {
    private Long placeId;
    private String type;
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
    private String playground;
    private String dayOff;
    private String representativeMenu;
    private String totalMenu;
    private String smokingYn;
    private String cardYn;
    private String takeoutYn;
    private String reserveInfo;
    private String openTime;
}
