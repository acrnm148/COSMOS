package com.cosmos.back.repository.review;


import com.cosmos.back.dto.response.review.ReviewUserResponseDto;

import com.cosmos.back.model.Review;
import com.cosmos.back.model.ReviewCategory;


import java.util.List;

public interface ReviewRepositoryCustom {

    // QueryDsl로 review 지우기
    public Long deleteReviewQueryDsl (Long reviewId);
    public Long deleteReviewCategoryQueryDsl (Long reviewId);
    public Long deleteReviewPlaceQueryDsl (Long reviewId);
    public Long deleteReviewImagesQueryDsl (Long reviewId);

    // QueryDsl로 장소별 review 불러오기
    public List<Review> findReviewInPlaceQueryDsl (Long placeId, Integer limit, Integer offset);

    // QueryDsl로 내 review 불러오기
    public List<Review> findReviewInUserQueryDsl (Long userSeq, Integer limit, Integer offset);

    // QueryDsl로 장소별로 커플 및 유저 review 불러오기
    public List<Review> findReviewInPlaceUserCoupleQueryDsl (Long userSeq, Long placeId, Integer limit, Integer offset);

    public Long deleteIndiReviewCategoryQueryDsl(Long reviewId);
}
