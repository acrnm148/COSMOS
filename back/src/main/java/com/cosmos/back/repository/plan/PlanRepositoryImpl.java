package com.cosmos.back.repository.plan;

import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.place.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /* QueryDsl로 관광 상세 정보 받아오기
    @Override
    public TourResponseDto findTourByPlaceIdQueryDsl(Long placeId) {
        QTour qTour = QTour.tour;

        return queryFactory.select(Projections.constructor(TourResponseDto.class,
                    qTour.id,
                    qTour.name,
                    qTour.phoneNumber,
                    qTour.latitude,
                    qTour.longitude,
                    qTour.thumbNailUrl,
                    qTour.detail,
                    qTour.parkingYn,
                    qTour.address,
                    qTour.img1,
                    qTour.img2,
                    qTour.img3,
                    qTour.img4,
                    qTour.img5,
                    qTour.type,
                    qTour.petYn,
                    qTour.introduce,
                    qTour.insideYn,
                    qTour.dayOff,
                    qTour.program
                ))
                .from(qTour)
                .where(qTour.id.eq(placeId))
                .fetchOne();
    }*/

}
