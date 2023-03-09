package com.cosmos.back.repository.review;

import com.cosmos.back.model.QReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long deleteReviewQueryDsl(Long reviewId, Long userSeq) {
        QReview qReview = QReview.review;

        Long execute = queryFactory
                .delete(qReview)
                .where(qReview.id.eq(reviewId).and(qReview.user.userSeq.eq(userSeq)))
                .execute();

        return execute;
    }
}
