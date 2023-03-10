package com.cosmos.back.repository.review;


import com.cosmos.back.dto.response.review.ReviewResponseDto;
import com.cosmos.back.model.Review;

import java.util.List;

public interface ReviewRepositoryCustom {

    // QueryDsl로 review 지우기
    public Long deleteReviewQueryDsl (Long reviewId);
    public Long deleteReviewCategoryQueryDsl (Long reviewId);
    public Long deleteReviewPlaceQueryDsl (Long reviewId);

    // QueryDsl로 장소별 review 불러오기
    public List<Review> findReviewInPlaceQueryDsl (Long placeId);
}
