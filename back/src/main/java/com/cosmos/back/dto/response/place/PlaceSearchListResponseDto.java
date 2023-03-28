package com.cosmos.back.dto.response.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceSearchListResponseDto {
    private Long placeId;
    private String name;
    private String address;
    private Double score;
    private String thumbNailUrl;
    private String detail;
    private String latitude;
    private String longitude;
    private String type;
    private Boolean like;

    public PlaceSearchListResponseDto (Long placeId, String name, String address, Double score, String thumbNailUrl, String detail, String latitude, String longitude, String type) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.score = score;
        this.thumbNailUrl = thumbNailUrl;
        this.detail = detail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }
}
