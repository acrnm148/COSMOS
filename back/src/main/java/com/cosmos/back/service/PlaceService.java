package com.cosmos.back.service;

import com.cosmos.back.annotation.RedisCached;
import com.cosmos.back.annotation.RedisCachedKeyParam;
import com.cosmos.back.dto.GugunDto;
import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.Image;
import com.cosmos.back.model.User;
import com.cosmos.back.model.UserPlace;
import com.cosmos.back.model.place.Gugun;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.model.place.Sido;
import com.cosmos.back.repository.place.GugunRepository;
import com.cosmos.back.repository.place.SidoRepository;
import com.cosmos.back.repository.place.UserPlaceRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final UserPlaceRepository userPlaceRepository;
    private final SidoRepository sidoRepository;
    private final GugunRepository gugunRepository;

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

    // QueryDsl로 쇼핑 상세 정보 받아오기
    public ShoppingResponseDto detailShopping (Long placeId) {
        return placeRepository.findShoppingByPlaceIdQueryDsl(placeId);
    }

    // QueryDsl로 레포츠 상세 정보 받아오기
    public LeisureResponseDto detailLeisure (Long placeId) {
        return placeRepository.findLeisureByPlaceIdQueryDsl(placeId);
    }

    // QueryDsl로 문화시설 상세 정보 받아오기
    public CultureResponseDto detailCulture (Long placeId) {
        return placeRepository.findCultureByPlaceIdQueryDsl(placeId);
    }

    // 장소 찜하기
    @Transactional
    public Long likePlace (Long placeId, Long userSeq) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        UserPlace userPlace = UserPlace.createUserPlace(user, place);

        UserPlace savedUserPlace = userPlaceRepository.save(userPlace);

        return savedUserPlace.getId();
    }

    // 장소 찜 삭제
    @Transactional
    public Long deleteLikePlace (Long placeId, Long userSeq) {
        Long execute = userPlaceRepository.deleteUserPlaceQueryDsl(placeId, userSeq);

        Place place = placeRepository.findById(placeId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        System.out.println(place.getUserPlaces().size());

        // 존재하지 않는 UserPlace 일 때 error 처리
        if (execute == 0) {
            throw new IllegalStateException("존재하지 않는 찜 입니다.");
        }

        return execute;
    }

    // 이름으로 장소 검색
    @RedisCached(key = "searchPlaceByName", expire = 60)
    public List<PlaceListResponseDto> searchPlacesByName (@RedisCachedKeyParam(key = "name") String name, Integer limit, Integer offset) {
        return placeRepository.findPlaceListByNameQueryDsl(name, limit, offset);
    }

    // 시도 리스트 받아오기
    //@RedisCached(key = "listSido", expire = 7200)
    public List<Sido> listSido () {
        return sidoRepository.findAll();
    }

    // 구군 리스트 받아오기
    @RedisCached(key = "listGugun", expire = 7200)
    public List<GugunDto> listGugun (@RedisCachedKeyParam(key = "code") String code) {
        List<Gugun> gugunList = gugunRepository.findBysidoCode(code);
        List<GugunDto> gugunDtoList = new ArrayList<>();
        for (Gugun item : gugunList) {
            gugunDtoList.add(GugunDto.builder()
                            .gugunCode(item.getGugunCode())
                            .gugunName(item.getGugunName())
                    .build());
        }
        return gugunDtoList;
    }

    // 시도구군으로 장소 검색
    @RedisCached(key = "searchPlacesBySidoGugun", expire = 7200)
    public List<PlaceListResponseDto> searchPlacesBySidoGugun (@RedisCachedKeyParam(key = "sido")String sido, @RedisCachedKeyParam(key = "gugun")String gugun, Integer limit, Integer offset) {
        List<PlaceListResponseDto> list = placeRepository.findPlaceListBySidoGugunQueryDsl(sido, gugun, limit, offset);
        System.out.println(list);
        return list;
    }

    // 장소 검색 자동 완성
    @RedisCached(key = "autoCompleteSearchPlacesByName", expire = 7200)
    public List<AutoCompleteResponseDto> autoCompleteSearchPlacesByName (@RedisCachedKeyParam(key = "name") String name) {
        return placeRepository.findPlaceListByNameAutoCompleteQueryDsl(name);
    }
}
