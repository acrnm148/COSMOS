package com.cosmos.back.controller;

import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
