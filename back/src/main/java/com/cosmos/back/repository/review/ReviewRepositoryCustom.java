package com.cosmos.back.repository.review;


public interface ReviewRepositoryCustom {

    // QueryDsl로 review 지우기
    public Long deleteReviewQueryDsl (Long reviewId, Long userSeq);
}
