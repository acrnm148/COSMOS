package com.cosmos.back.controller;

import com.cosmos.back.annotation.RedisCached;
import com.cosmos.back.dto.GugunDto;
import com.cosmos.back.dto.request.AutoCompleteRequestDto;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.place.Gugun;
import com.cosmos.back.model.place.Sido;
import com.cosmos.back.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "place", description = "장소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlaceApiController {

    private final PlaceService placeService;

    @Operation(summary = "관광 정보를 가져옴", description = "관광 정보를 가져옴")
    @GetMapping("/places/tours/{placeId}")
    public ResponseEntity<TourResponseDto> detailTour(@PathVariable Long placeId) {
        TourResponseDto tourResponseDto = placeService.detailTour(placeId);

        return new ResponseEntity<>(tourResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "축제 정보를 가져옴", description = "축제 정보를 가져옴")
    @GetMapping("/places/festivals/{placeId}")
    public ResponseEntity<FestivalResponseDto> detailFestival(@PathVariable Long placeId) {
        FestivalResponseDto festivalResponseDto = placeService.detailFestival(placeId);

        return new ResponseEntity<>(festivalResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "숙박 정보를 가져옴", description = "숙박 정보를 가져옴")
    @GetMapping("/places/accommodations/{placeId}")
    public ResponseEntity<AccommodationResponseDto> detailAccommodation(@PathVariable Long placeId) {
        AccommodationResponseDto accommodationResponseDto = placeService.detailAccommodation(placeId);

        return new ResponseEntity<>(accommodationResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "음식점 정보를 가져옴", description = "음식점 정보를 가져옴")
    @GetMapping("/places/restaurants/{placeId}")
    public ResponseEntity<RestaurantResponseDto> detailRestaurant(@PathVariable Long placeId) {
        RestaurantResponseDto restaurantResponseDto = placeService.detailRestaurant(placeId);

        return new ResponseEntity<>(restaurantResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "카페 정보를 가져옴", description = "카페 정보를 가져옴")
    @GetMapping("/places/cafes/{placeId}")
    public ResponseEntity<CafeResponseDto> detailCafe(@PathVariable Long placeId) {
        CafeResponseDto cafeResponseDto = placeService.detailCafe(placeId);

        return new ResponseEntity<>(cafeResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "쇼핑 정보를 가져옴", description = "쇼핑 정보를 가져옴")
    @GetMapping("/places/shoppings/{placeId}")
    public ResponseEntity<ShoppingResponseDto> detailShopping(@PathVariable Long placeId) {
        ShoppingResponseDto shoppingResponseDto = placeService.detailShopping(placeId);

        return new ResponseEntity<>(shoppingResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "레포츠 정보를 가져옴", description = "레포츠 정보를 가져옴")
    @GetMapping("/places/leisures/{placeId}")
    public ResponseEntity<LeisureResponseDto> detailLeisure(@PathVariable Long placeId) {
        LeisureResponseDto leisureResponseDto = placeService.detailLeisure(placeId);

        return new ResponseEntity<>(leisureResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "문화시설 정보를 가져옴", description = "문화시설 정보를 가져옴")
    @GetMapping("/places/cultures/{placeId}")
    public ResponseEntity<CultureResponseDto> detailCulture(@PathVariable Long placeId) {
        CultureResponseDto cultureResponseDto = placeService.detailCulture(placeId);

        return new ResponseEntity<>(cultureResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "장소 찜하기", description = "장소를 찜 함")
    @GetMapping("/places/{placeId}/users/{userSeq}")
    public ResponseEntity<Long> likePlace(@PathVariable Long placeId, @PathVariable Long userSeq) {
        Long id = placeService.likePlace(placeId, userSeq);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "장소 찜 삭제", description = "장소를 찜 해제함")
    @DeleteMapping("/places/{placeId}/users/{userSeq}")
    public ResponseEntity<Long> deleteLikePlace(@PathVariable Long placeId, @PathVariable Long userSeq) {
        Long execute = placeService.deleteLikePlace(placeId, userSeq);

        return new ResponseEntity<>(execute, HttpStatus.OK);
    }

    @Operation(summary = "이름으로 장소 검색", description = "이름으로 장소 검색")
    @GetMapping("/places/{name}")
    public ResponseEntity<List> search(@PathVariable String name, @RequestParam Integer limit, @RequestParam Integer offset) {
        List<PlaceListResponseDto> list = placeService.searchPlacesByName(name, limit, offset);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 시도 리스트 가져오기
    @Operation(summary = "시도 리스트 가져오기", description = "시/도 코드, 시/도 이름을 반환한다")
    @GetMapping("/sido")
    public ResponseEntity<List> sidoList() {
        List<Sido> list = placeService.listSido();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 시도에 따른 구군 리스트 가져오기
    @Operation(summary = "구군 리스트 가져오기", description = "시/도의 code 번호를 queryString형식으로 보내주어야 한다.(ex: /gugun/11)")
    @GetMapping("/gugun/{sido}")
    public ResponseEntity<List> gugunList(@PathVariable String sido) {
        List<GugunDto> list = placeService.listGugun(sido);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "시도구군으로 장소 검색", description = "시도구군으로 장소 검색")
    @GetMapping("/places/search/sido/{sido}/gugun/{gugun}")
    public ResponseEntity<List> search(@PathVariable String sido, @PathVariable String gugun, @RequestParam Integer limit, @RequestParam Integer offset) {
        List<PlaceListResponseDto> list = placeService.searchPlacesBySidoGugun(sido, gugun, limit, offset);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "시/도, 구/군, 검색어, 검색 필터으로 검색", description = "시/도, 구/군, 검색어, 검색 필터를 자유롭게 입력 후 요청(limit과 offset 설정 필요)")
    @GetMapping("/places/search/sido/{sido}/gugun/{gugun}/text/{text}/filter/{filter}")
    public ResponseEntity<List> searchPlace(@PathVariable String sido, @PathVariable String gugun, @PathVariable String text, @PathVariable String filter, @RequestParam Integer limit, @RequestParam Integer offset) {
        List<PlaceSearchListResponseDto> list = placeService
    }

    @Operation(summary = "검색어 자동완성", description = "검색어 자동완성")
    @PostMapping("/places/auto")
    public ResponseEntity<List> search(@RequestBody AutoCompleteRequestDto dto) {
        List<AutoCompleteResponseDto> autoCompleteResponseDto = placeService.autoCompleteSearchPlacesByName(dto.getSearchWord());

        return new ResponseEntity<>(autoCompleteResponseDto, HttpStatus.OK);
    }
}
