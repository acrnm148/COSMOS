package com.cosmos.back.repository.place;

import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.place.Place;

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

    // QueryDsl로 장소 리스트 가져오기(시도구군) with Pagination
    public List<PlaceListResponseDto> findPlaceListBySidoGugunQueryDsl(String sido, String gugun, Integer limit, Integer offset);

    // QueryDsl로 장소 리스트 가져오기(시/도, 구/군, 검색어, 검색필터) with Pagination
    public List<PlaceSearchListResponseDto> findPlaceListBySidoGugunTextFilterQueryDsl(Long userSeq, String sido, String gugun, String text, String filter, Integer limit, Integer offset);

    // QueryDsl로 장소 검색 자동 완성 (Limit = 10)
    public List<AutoCompleteResponseDto> findPlaceListByNameAutoCompleteQueryDsl(String searchWord);

    // QueryDsl로 장소별 별점 평점 가져오기
    public Double findScoreByPlaceIdQueryDsl(Long placeId);

    // QueryDsl로 시도, 구군, 타입별 장소 리스트 가져오기
    public List<Place> findAllByTypeAndSidoAndGugun(String type, String sido, String gugun);

    // QueryDsl로 찜한 거 가져오기
    public boolean findPlaceLikeByPlaceIdUserSeqQueryDsl(Long placeId, Long userSeq);

}
