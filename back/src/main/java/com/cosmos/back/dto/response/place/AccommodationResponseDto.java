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
}
