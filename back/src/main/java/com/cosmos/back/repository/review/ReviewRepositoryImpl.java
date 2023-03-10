package com.cosmos.back.repository.review;

import com.cosmos.back.dto.response.review.ReviewResponseDto;
import com.cosmos.back.model.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    //review 삭제
    @Override
    public Long deleteReviewQueryDsl(Long reviewId) {
        QReview qReview = QReview.review;

        Long execute_review = queryFactory
                .delete(qReview)
                .where(qReview.id.eq(reviewId))
                .execute();

        return  execute_review;
    }

    //reviewCategory 삭제
    @Override
    public Long deleteReviewCategoryQueryDsl(Long reviewId) {
        QReviewCategory qReviewCategory = QReviewCategory.reviewCategory;

        Long executeReviewCategory = queryFactory
                .delete(qReviewCategory)
                .where(qReviewCategory.review.id.eq(reviewId))
                .execute();

        return executeReviewCategory;
    }

    //reviewPlace 삭제
    @Override
    public Long deleteReviewPlaceQueryDsl(Long reviewId) {
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;

        Long executeReviewPlace = queryFactory
                .delete(qReviewPlace)
                .where(qReviewPlace.review.id.eq(reviewId))
                .execute();
        return executeReviewPlace;
    }

    // 장소에 대한 리뷰 모두 불러오기
    @Override
    public List<Review> findReviewInPlaceQueryDsl(Long placeId) {
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;
        QReviewCategory qReviewCategory = QReviewCategory.reviewCategory;

        return queryFactory.selectFrom(qReview)
                .join(qReviewPlace)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .where(qReviewPlace.place.id.eq(placeId))
                .join(qReviewCategory)
                .on(qReview.id.eq(qReviewCategory.review.id))
                .fetch();
    }
}