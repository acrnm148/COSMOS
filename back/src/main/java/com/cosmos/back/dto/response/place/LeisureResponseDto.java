package com.cosmos.back.dto.response.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeisureResponseDto {
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
    private String acceptablePeople;
    private String dayOff;
    private String parkingCost;
    private String openTime;
    private String openPeriod;
    private String price;
    private String ageRange;
    private String petYn;
    private Double score;
    private boolean like;

    public LeisureResponseDto(Long placeId, String name, String phoneNumber, String latitude, String longitude, String thumbNailUrl, String detail, String parkingYn, String address, String img1, String img2, String img3, String img4, String img5, String type, String acceptablePeople, String dayOff, String parkingCost, String openTime, String openPeriod, String price, String ageRange, String petYn, Double score) {
        this.placeId = placeId;
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
        this.type = type;
        this.acceptablePeople = acceptablePeople;
        this.dayOff = dayOff;
        this.parkingCost = parkingCost;
        this.openTime = openTime;
        this.openPeriod = openPeriod;
        this.price = price;
        this.ageRange = ageRange;
        this.petYn = petYn;
        this.score = score;
    }
}
