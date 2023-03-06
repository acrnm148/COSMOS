package com.cosmos.back.repository.place;

import com.cosmos.back.dto.response.place.FestivalResponseDto;
import com.cosmos.back.dto.response.place.TourResponseDto;

public interface PlaceRepositoryCustom {

    // QueryDsl로 관광 상세 정보 받아오기
    public TourResponseDto findTourByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 축제 상세 정보 받아오기
    public FestivalResponseDto findFestivalByPlaceIdQueryDsl(Long placeId);
}
