package com.cosmos.back.service;

import com.cosmos.back.annotation.RedisCached;
import com.cosmos.back.annotation.RedisCachedKeyParam;
import com.cosmos.back.annotation.RedisEvict;
import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.dto.response.review.ReviewResponseDto;
import com.cosmos.back.dto.response.review.ReviewUserResponseDto;
import com.cosmos.back.model.*;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.review.ReviewAdjectiveRepository;
import com.cosmos.back.repository.review.ReviewCategoryRepository;
import com.cosmos.back.repository.review.ReviewNounRepository;
import com.cosmos.back.repository.review.ReviewRepository;
import com.cosmos.back.repository.reviewplace.ReviewPlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCategoryRepository reviewCategoryRepository;
    private final PlaceRepository placeRepository;
    private final ReviewPlaceRepository reviewPlaceRepository;
    private final ReviewAdjectiveRepository reviewAdjectiveRepository;
    private final ReviewNounRepository reviewNounRepository;

    //리뷰 쓰기
    @Transactional
    @RedisEvict(key = "review")
    public Long createReview(ReviewRequestDto dto, @RedisCachedKeyParam(key = "userSeq") Long userSeq) {
        //Long userSeq = dto.getUserSeq();

        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Place place = placeRepository.findById(dto.getPlaceId()).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Review review = Review.createReview(user, dto.getContents(), dto.getScore());
        Review new_review = reviewRepository.save(review);

        ReviewPlace reviewPlace = ReviewPlace.createReviewPlace(review, place);
        reviewPlaceRepository.save(reviewPlace);

        for (String category : dto.getCategories()) {
            ReviewCategory reviewCategory = ReviewCategory.createReviewCategory(category, review);
            reviewCategoryRepository.save(reviewCategory);
        }

        return new_review.getId();
    }

    // 리뷰 삭제하기
    @Transactional
    @RedisEvict(key = "review")
    public Long deleteReview (Long reviewId, @RedisCachedKeyParam(key = "userSeq") Long userSeq) {
        Long executeReviewCategory = reviewRepository.deleteReviewCategoryQueryDsl(reviewId);
        Long executeReviewPlace = reviewRepository.deleteReviewPlaceQueryDsl(reviewId);
        Long executeReview = reviewRepository.deleteReviewQueryDsl(reviewId);

        Long execute = executeReviewCategory * executeReviewPlace * executeReview;

        // 존재하지 않는 review 일 때 error 처리
        if (execute == 0) {
            throw new IllegalStateException("존재하지 않는 리뷰입니다.");
        }

        return execute;
    }

    // 장소별 리뷰 모두 불러오기
    public List<ReviewResponseDto> findReviesInPlace (Long placeId) {
        List<Review> review = reviewRepository.findReviewInPlaceQueryDsl(placeId);

        List<ReviewResponseDto> list = new ArrayList<>();
        for (Review r : review) {
            ReviewResponseDto dto = ReviewResponseDto.builder().reviewId(r.getId()).categories(r.getReviewCategories()).score(r.getScore()).contents(r.getContents()).userId(r.getUser().getUserSeq()).build();
            list.add(dto);
        }

        return list;
    }

    // 내 리뷰 모두 불러오기
    @RedisCached(key = "review", expire = 240)
    public List<ReviewUserResponseDto> findReviewsInUser (@RedisCachedKeyParam(key = "userSeq") Long userSeq) {
        List<Review> review = reviewRepository.findReviewInUserQueryDsl(userSeq);

        List<ReviewUserResponseDto> list = new ArrayList<>();
        for (Review r : review) {
            ReviewUserResponseDto dto = ReviewUserResponseDto.builder()
                    .reviewId(r.getId())
                    .categories(r.getReviewCategories())
                    .score(r.getScore())
                    .contents(r.getContents())
//                    .placeId(r.getReviewPlaces().get(0).getPlace().getId())
                    .placeId(r.getReviewPlaces().get(0).getPlace().getId())
                    .build();
            list.add(dto);
        }

        return list;
    }

    // 리뷰 수정
    @RedisEvict(key = "review")
    @Transactional
    public Long changeReview (Long reviewId, ReviewRequestDto dto, @RedisCachedKeyParam(key = "userSeq") Long userSeq) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        review.setContents(dto.getContents());
        review.setScore(dto.getScore());

        List<ReviewCategory> list = reviewCategoryRepository.findAllByReviewId(reviewId);

        for (ReviewCategory rc : list) {
            reviewCategoryRepository.deleteById(rc.getId());
        }

        for (String category : dto.getCategories()) {
            ReviewCategory reviewCategory = ReviewCategory.createReviewCategory(category, review);
            reviewCategoryRepository.save(reviewCategory);
        }

        return review.getId();
    }

    // 리뷰 닉네임 생성하기
    public String findReviewNickName () {
        List<Adjective> adjectives = reviewAdjectiveRepository.findAll();
        List<Noun> nouns = reviewNounRepository.findAll();

        Integer adjectivesSize = adjectives.size();
        Integer nounsSize = nouns.size();

        Integer randomAdjectiveIdx = (int) (Math.random() * adjectivesSize);
        Integer randomNounIdx = (int) (Math.random() * nounsSize);

        String randomAdjective = adjectives.get(randomAdjectiveIdx).getContents();
        String randomNoun = nouns.get(randomNounIdx).getContents();

        return randomAdjective + " " + randomNoun;
    }
}
