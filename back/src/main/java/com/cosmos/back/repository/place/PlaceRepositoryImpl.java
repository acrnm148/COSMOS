package com.cosmos.back.repository.place;

import com.cosmos.back.dto.response.place.FestivalResponseDto;
import com.cosmos.back.dto.response.place.TourResponseDto;
import com.cosmos.back.model.place.QFestival;
import com.cosmos.back.model.place.QTour;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // QueryDsl로 관광 상세 정보 받아오기
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
                    qTour.parking_yn,
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
                    qTour.dayOff
                ))
                .where(qTour.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 축제 상세 정보 받아오기
    @Override
    public FestivalResponseDto findFestivalByPlaceIdQueryDsl(Long placeId) {
        QFestival qFestival = QFestival.festival;

        return queryFactory.select(Projections.constructor(FestivalResponseDto.class,
                        qFestival.id,
                        qFestival.name,
                        qFestival.phoneNumber,
                        qFestival.latitude,
                        qFestival.longitude,
                        qFestival.thumbNailUrl,
                        qFestival.detail,
                        qFestival.parking_yn,
                        qFestival.address,
                        qFestival.img1,
                        qFestival.img2,
                        qFestival.img3,
                        qFestival.img4,
                        qFestival.img5,
                        qFestival.type,
                        qFestival.petYn,
                        qFestival.introduce,
                        qFestival.startDate,
                        qFestival.endDate,
                        qFestival.price,
                        qFestival.takenTime
                ))
                .where(qFestival.id.eq(placeId))
                .fetchOne();
    }
}
