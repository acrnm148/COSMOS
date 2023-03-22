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
    private boolean like;

    public RestaurantResponseDto(Long placeId, String type, String name, String phoneNumber, String latitude, String longitude, String thumbNailUrl, String detail, String parkingYn, String address, String img1, String img2, String img3, String img4, String img5, String playground, String dayOff, String representativeMenu, String totalMenu, String smokingYn, String cardYn, String takeoutYn, String reserveInfo, String openTime) {
        this.placeId = placeId;
        this.type = type;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.thumbNailUrl = thumbNailUrl;
        this.detail = detail;
        this.parkingYn = parkingYn;
        this.address = address;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
        this.playground = playground;
        this.dayOff = dayOff;
        this.representativeMenu = representativeMenu;
        this.totalMenu = totalMenu;
        this.smokingYn = smokingYn;
        this.cardYn = cardYn;
        this.takeoutYn = takeoutYn;
        this.reserveInfo = reserveInfo;
        this.openTime = openTime;
    }
}
