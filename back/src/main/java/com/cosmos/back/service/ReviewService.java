package com.cosmos.back.service;

import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.model.Review;
import com.cosmos.back.model.ReviewCategory;
import com.cosmos.back.model.User;
import com.cosmos.back.repository.UserRepository;
import com.cosmos.back.repository.review.ReviewCategoryRepository;
import com.cosmos.back.repository.review.ReviewRepository;
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

    //리뷰 쓰기
    @Transactional
    public Long createReview(ReviewRequestDto dto) {
        Long userSeq = dto.getUserSeq();
        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Review review = Review.createReview(user, dto.getContents(), dto.getScore());

        Review review1 = reviewRepository.save(review);

        for (String category : dto.getCategories()) {
            ReviewCategory reviewCategory = ReviewCategory.createReviewCategory(category, review);
            reviewCategoryRepository.save(reviewCategory);
        }

        return review1.getId();
    }
}
