package com.cosmos.back.repository.place;

import com.cosmos.back.dto.response.place.*;

import java.util.List;

public interface PlaceRepositoryCustom {

    // QueryDsl로 관광 상세 정보 받아오기
    public TourResponseDto findTourByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 축제 상세 정보 받아오기
    public FestivalResponseDto findFestivalByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 숙박 상세 정보 받아오기
    public AccommodationResponseDto findAccommodationByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 음식점 상세 정보 받아오기
    public RestaurantResponseDto findRestaurantByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 카페 상세 정보 받아오기
    public CafeResponseDto findCafeByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 쇼핑 상세 정보 받아오기
    public ShoppingResponseDto findShoppingByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 레포츠 상세 정보 받아오기
    public LeisureResponseDto findLeisureByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 문화시설 상세 정보 받아오기
    public CultureResponseDto findCultureByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 장소 리스트 가져오기 with Pagination
    public List<PlaceListResponseDto> findPlaceListByNameQueryDsl(String name, Integer limit, Integer offset);
}
