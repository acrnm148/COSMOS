package com.cosmos.back.service;

import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.model.Review;
import com.cosmos.back.model.ReviewCategory;
import com.cosmos.back.model.ReviewPlace;
import com.cosmos.back.model.User;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.review.ReviewCategoryRepository;
import com.cosmos.back.repository.review.ReviewRepository;
import com.cosmos.back.repository.reviewplace.ReviewPlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCategoryRepository reviewCategoryRepository;
    private final PlaceRepository placeRepository;
    private final ReviewPlaceRepository reviewPlaceRepository;

    //리뷰 쓰기
    @Transactional
    public Long createReview(ReviewRequestDto dto) {
        Long userSeq = dto.getUserSeq();

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
}
