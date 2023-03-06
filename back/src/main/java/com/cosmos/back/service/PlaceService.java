package com.cosmos.back.service;

import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.repository.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceRepository placeRepository;

    // QueryDsl로 관광 상세 정보 받아오기
    public TourResponseDto detailTour (Long placeId) {
        return placeRepository.findTourByPlaceIdQueryDsl(placeId);
    }

    // QueryDsl로 축제 상세 정보 받아오기
    public FestivalResponseDto detailFestival (Long placeId) {
        return placeRepository.findFestivalByPlaceIdQueryDsl(placeId);
    }

    // QueryDsl로 숙박 상세 정보 받아오기
    public AccommodationResponseDto detailAccommodation (Long placeId) {
        return placeRepository.findAccommodationByPlaceIdQueryDsl(placeId);
    }

    // QueryDsl로 음식점 상세 정보 받아오기
    public RestaurantResponseDto detailRestaurant (Long placeId) {
        return placeRepository.findRestaurantByPlaceIdQueryDsl(placeId);
    }

    // QueryDsl로 카페 상세 정보 받아오기
    public CafeResponseDto detailCafe (Long placeId) {
        return placeRepository.findCafeByPlaceIdQueryDsl(placeId);
    }

}
