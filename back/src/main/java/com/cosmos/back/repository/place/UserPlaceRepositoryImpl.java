package com.cosmos.back.repository.place;

import com.cosmos.back.dto.response.place.PlaceListResponseDto;
import com.cosmos.back.model.QReview;
import com.cosmos.back.model.QReviewPlace;
import com.cosmos.back.model.QUser;
import com.cosmos.back.model.QUserPlace;
import com.cosmos.back.model.place.QPlace;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserPlaceRepositoryImpl implements UserPlaceRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Long deleteUserPlaceQueryDsl (Long placeId, Long userSeq) {
        QUserPlace qUserPlace = QUserPlace.userPlace;

        Long execute = queryFactory
                .delete(qUserPlace)
                .where(qUserPlace.place.id.eq(placeId).and(qUserPlace.user.userSeq.eq(userSeq)))
                .execute();

        return execute;
    }

    @Override
    public List<PlaceListResponseDto> findLikePlaces(Long userSeq, Integer limit, Integer offset) {
        QUserPlace qUserPlace = QUserPlace.userPlace;
        QPlace qPlace = QPlace.place;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;

        return queryFactory.select(Projections.constructor(PlaceListResponseDto.class,
                qPlace.id,
                qPlace.name,
                qPlace.address,
                qReview.score.avg(),
                qPlace.thumbNailUrl,
                qPlace.detail,
                qPlace.type
                ))
                .from(qPlace)
                .rightJoin(qUserPlace)
                .on(qPlace.id.eq(qUserPlace.place.id))
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.place.id.eq(qPlace.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(qUserPlace.user.userSeq.eq(userSeq))
                .groupBy(qPlace.id)
                .limit(limit)
                .offset(offset)
                .fetch();
    }
}
