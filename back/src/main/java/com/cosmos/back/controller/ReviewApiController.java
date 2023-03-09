package com.cosmos.back.controller;

import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.dto.response.place.TourResponseDto;
import com.cosmos.back.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "review", description = "리뷰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewApiController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "리뷰를 등록함")
    @PostMapping("/reviews")
    public ResponseEntity<Long> detailTour(@RequestBody ReviewRequestDto dto) {
        Long reviewId = reviewService.createReview(dto);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }
}
