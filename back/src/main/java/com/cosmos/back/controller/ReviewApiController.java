package com.cosmos.back.controller;

import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.dto.response.review.ReviewResponseDto;
import com.cosmos.back.model.Review;
import com.cosmos.back.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "review", description = "리뷰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewApiController {

    private final ReviewService reviewService;

    // 리뷰 생성하기
    @Operation(summary = "리뷰 등록", description = "리뷰를 등록함, 헤더에 토큰 필요")
    @PostMapping("/reviews")
    public ResponseEntity<Long> createReview(@RequestBody ReviewRequestDto dto) {
        Long reviewId = reviewService.createReview(dto);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    // 리뷰 삭제하기
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제함, 헤더에 토큰 필요")
    @DeleteMapping("/reviews/{reviewId}/users/{userSeq}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId, @PathVariable Long userSeq) {
        reviewService.deleteReview(reviewId, userSeq);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 장소별 리뷰 모두 불러오기
    @Operation(summary = "장소별 리뷰 모두 불러오기", description = "장소별로 리뷰를 모두 불러온다")
    @GetMapping("/reviews/places/{placeId}")
    public ResponseEntity<List> getReviesInPlace(@PathVariable Long placeId) {
        List<ReviewResponseDto> reviewsInPlace = reviewService.getReviesInPlace(placeId);

        return new ResponseEntity<>(reviewsInPlace, HttpStatus.OK);
    }
}