package com.cosmos.back.repository.place;

import com.cosmos.back.model.QUserPlace;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
