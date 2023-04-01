package com.cosmos.back.repository.review;

import com.cosmos.back.dto.response.review.ReviewUserResponseDto;
import com.cosmos.back.model.QReview;
import com.cosmos.back.model.QReviewCategory;
import com.cosmos.back.model.QReviewPlace;
import com.cosmos.back.model.QUser;
import com.cosmos.back.model.place.QPlace;
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

    //IndiReviewCategory 삭제
    @Override
    public Long deleteIndiReviewCategoryQueryDsl(Long reviewId) {
        QIndiReviewCategory qIndiReviewCategory = QIndiReviewCategory.indiReviewCategory;

        Long executeReviewCategory = queryFactory
                .delete(qIndiReviewCategory)
                .where(qIndiReviewCategory.review.id.eq(reviewId))
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

    @Override
    public Long deleteReviewImagesQueryDsl(Long reviewId) {
        QImage qImage = QImage.image;

        Long executeReviewImages = queryFactory
                .delete(qImage)
                .where(qImage.review.id.eq(reviewId))
                .execute();

        return executeReviewImages;
    }

    // 장소별로 유저, 커플아이디를 이용해 리뷰 모두 불러오기
    @Override
    public List<Review> findReviewInPlaceUserCoupleQueryDsl(Long userSeq, Long placeId) {
        QReview qReview = QReview.review;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QImage qImage = QImage.image;

        return queryFactory.selectFrom(qReview)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.review.id.eq(qReview.id))
                .leftJoin(qImage)
                .on(qReview.id.eq(qImage.review.id))
                .distinct()
                .where(qReview.user.userSeq.eq(userSeq)
                        .and(qReviewPlace.place.id.eq(placeId)))
                .fetch();
    }


    // 장소에 대한 리뷰 모두 불러오기
    @Override
    public List<Review> findReviewInPlaceQueryDsl(Long placeId) {
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;
        QReviewCategory qReviewCategory = QReviewCategory.reviewCategory;
        QIndiReviewCategory qIndiReviewCategory = QIndiReviewCategory.indiReviewCategory;

        return queryFactory.selectFrom(qReview)
                .distinct()
                .join(qReviewPlace)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .where(qReviewPlace.place.id.eq(placeId))
                .join(qReviewCategory)
                .on(qReview.id.eq(qReviewCategory.review.id))
                .join(qIndiReviewCategory)
                .on(qReview.id.eq(qIndiReviewCategory.review.id))
                .fetch();
    }

    // 내 리뷰 모두 불러오기
    @Override
    public List<Review> findReviewInUserQueryDsl(Long userSeq) {
        QReview qReview = QReview.review;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReviewCategory qReviewCategory = QReviewCategory.reviewCategory;
        QIndiReviewCategory qIndiReviewCategory = QIndiReviewCategory.indiReviewCategory;
        QImage qImage = QImage.image;

        return queryFactory.selectFrom(qReview)
                .distinct()
                .where(qReview.user.userSeq.eq(userSeq))
                .join(qReviewPlace)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .join(qReviewCategory)
                .on(qReview.id.eq(qReviewCategory.review.id))
                .join(qIndiReviewCategory)
                .on(qReview.id.eq(qIndiReviewCategory.review.id))
                .join(qImage)
                .on(qReview.id.eq(qImage.review.id))
                .fetch();
    }
}
