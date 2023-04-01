package com.cosmos.back.controller;

import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.dto.response.place.TourResponseDto;
import com.cosmos.back.dto.response.review.ReviewUserResponseDto;
import com.cosmos.back.dto.response.review.ReviewResponseDto;
import com.cosmos.back.model.Review;
import com.cosmos.back.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;

@Tag(name = "review", description = "리뷰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewApiController {

    private final ReviewService reviewService;

    // 리뷰 생성하기(완료)
    @Operation(summary = "리뷰 등록", description = "리뷰를 등록함, 헤더에 토큰 필요")
    @PostMapping("/reviews/users/{userSeq}")
    public ResponseEntity<Long> createReview(@PathVariable Long userSeq, @RequestBody ReviewRequestDto dto) {
        Long reviewId = reviewService.createReview(dto, userSeq);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    // 리뷰 삭제하기(완료)
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제함, 헤더에 토큰 필요")
    @DeleteMapping("/reviews/{reviewId}/users/{userSeq}")
    public ResponseEntity<Long> deleteReview(@PathVariable Long reviewId, @PathVariable Long userSeq) {
        Long id = reviewService.deleteReview(reviewId, userSeq);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // 장소별로 유저, 커플 유저의 리뷰 모두 불러오기
    @Operation(summary = "장소별로 유저, 커플 유저의 리뷰 모두 불러오기", description = "userSeq, coupleId(필수x), placeId를 입력해주면 커플이 있을 경우 커플 모두의 리뷰, 솔로인 경우 솔로만 리뷰 조회 [프런트에서 리뷰 순서는 정해줘야할 듯합니다.]")
    @GetMapping(value = {"/reviews/users/{userSeq}/coupleId/{coupleId}/places/{placeId}",
            "/reviews/users/{userSeq}/coupleId/places/{placeId}"
    })
    public ResponseEntity<List> findReviewInPlaceAndUser(@PathVariable Long userSeq, @PathVariable(required = false) Long coupleId, @PathVariable Long placeId) {
        if (coupleId == null) {coupleId = null;} // 커플 아이디가 없을 경우
        List<ReviewResponseDto> list = reviewService.findReviewsInPlaceUserCouple(userSeq, coupleId, placeId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    // 장소별 리뷰 모두 불러오기(완료)
    @Operation(summary = "장소별 리뷰 모두 불러오기", description = "장소별로 리뷰를 모두 불러온다, placeId만 입력")
    @GetMapping("/reviews/places/{placeId}")
    public ResponseEntity<List> findReviewsInPlace(@PathVariable Long placeId) {
        List<ReviewResponseDto> reviewsInPlace = reviewService.findReviewsInPlace(placeId);
        if (reviewsInPlace.size() > 0) {
            return new ResponseEntity<>(reviewsInPlace, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // 내 리뷰 모두 불러오기
    @Operation(summary = "내 리뷰 모두 불러오기", description = "내가 쓴 리뷰를 모두 불러온다")
    @GetMapping("/reviews/users/{userSeq}")
    public ResponseEntity<List> findReviwesInUser(@PathVariable Long userSeq) {
        System.out.println("userSeq = " + userSeq);
        List<ReviewUserResponseDto> reviewsInUser = reviewService.findReviewsInUser(userSeq);
        return new ResponseEntity<>(reviewsInUser, HttpStatus.OK);
    }

    // 리뷰 수정
    @Operation(summary = "내 리뷰 수정하기", description = "내가 쓴 리뷰를 수정한다")
    @PutMapping("/reviews/{reviewId}/users/{userSeq}")
    public ResponseEntity<Long> changeReview(@PathVariable Long reviewId, @PathVariable Long userSeq, @RequestBody ReviewRequestDto dto) {
        Long id = reviewService.changeReview(reviewId, dto, userSeq);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // 리뷰 닉네임 생성(완료)
    @Operation(summary = "리뷰 닉네임 생성하기", description = "리뷰 닉네임 생성한다")
    @GetMapping("/reviews/nickname")
    public ResponseEntity<String> findReviewNickName() {
        String reviewNickName = reviewService.findReviewNickName();
        return new ResponseEntity<>(reviewNickName, HttpStatus.OK);
    }
}
