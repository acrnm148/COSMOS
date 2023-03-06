package com.cosmos.back.controller;

import com.cosmos.back.dto.response.place.FestivalResponseDto;
import com.cosmos.back.dto.response.place.TourResponseDto;
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
}
