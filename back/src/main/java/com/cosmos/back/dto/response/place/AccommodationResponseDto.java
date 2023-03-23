package com.cosmos.back.dto.response.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccommodationResponseDto {
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
    private String acceptablePeople;
    private String roomNum;
    private String roomType;
    private String cookYn;
    private String checkIn;
    private String checkOut;
    private String reservationPage;
    private String pickupYn;
    private String karaokeYn;
    private String bbqYn;
    private String gymYn;
    private String refund;
    private boolean like;

    public AccommodationResponseDto(Long placeId, String type, String name, String phoneNumber, String latitude, String longitude, String thumbNailUrl, String detail, String parkingYn, String address, String img1, String img2, String img3, String img4, String img5, String acceptablePeople, String roomNum, String roomType, String cookYn, String checkIn, String checkOut, String reservationPage, String pickupYn, String karaokeYn, String bbqYn, String gymYn, String refund) {
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
        this.acceptablePeople = acceptablePeople;
        this.roomNum = roomNum;
        this.roomType = roomType;
        this.cookYn = cookYn;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reservationPage = reservationPage;
        this.pickupYn = pickupYn;
        this.karaokeYn = karaokeYn;
        this.bbqYn = bbqYn;
        this.gymYn = gymYn;
        this.refund = refund;
    }
}
