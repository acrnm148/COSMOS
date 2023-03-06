package com.cosmos.back.repository.place;

import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.place.*;
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
                    qTour.dayOff
                ))
                .from(qTour)
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
                        qFestival.parkingYn,
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
                .from(qFestival)
                .where(qFestival.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 숙박 상세 정보 받아오기
    @Override
    public AccommodationResponseDto findAccommodationByPlaceIdQueryDsl(Long placeId) {
        QAccommodation qAccommodation = QAccommodation.accommodation;
        return queryFactory.select(Projections.constructor(AccommodationResponseDto.class,
                qAccommodation.id,
                qAccommodation.name,
                qAccommodation.phoneNumber,
                qAccommodation.latitude,
                qAccommodation.longitude,
                qAccommodation.thumbNailUrl,
                qAccommodation.detail,
                qAccommodation.parkingYn,
                qAccommodation.address,
                qAccommodation.img1,
                qAccommodation.img2,
                qAccommodation.img3,
                qAccommodation.img4,
                qAccommodation.img5,
                qAccommodation.type,
                qAccommodation.acceptablePeople,
                qAccommodation.bbqYn,
                qAccommodation.checkIn,
                qAccommodation.checkOut,
                qAccommodation.cookYn,
                qAccommodation.gymYn,
                qAccommodation.karaokeYn,
                qAccommodation.pickupYn,
                qAccommodation.refund,
                qAccommodation.reservationPage,
                qAccommodation.roomNum,
                qAccommodation.roomType
                ))
                .from(qAccommodation)
                .where(qAccommodation.id.eq(placeId))
                .fetchOne();
    }

    @Override
    public RestaurantResponseDto findRestaurantByPlaceIdQueryDsl(Long placeId) {
        QRestaurant qRestaurant = QRestaurant.restaurant;
        return queryFactory.select(Projections.constructor(RestaurantResponseDto.class,
                        qRestaurant.id,
                        qRestaurant.name,
                        qRestaurant.phoneNumber,
                        qRestaurant.latitude,
                        qRestaurant.longitude,
                        qRestaurant.thumbNailUrl,
                        qRestaurant.detail,
                        qRestaurant.parkingYn,
                        qRestaurant.address,
                        qRestaurant.img1,
                        qRestaurant.img2,
                        qRestaurant.img3,
                        qRestaurant.img4,
                        qRestaurant.img5,
                        qRestaurant.type,
                        qRestaurant.cardYn,
                        qRestaurant.smokingYn,
                        qRestaurant.dayOff,
                        qRestaurant.playground,
                        qRestaurant.representativeMenu,
                        qRestaurant.reserveInfo,
                        qRestaurant.takeoutYn,
                        qRestaurant.totalMenu,
                        qRestaurant.openTime
                ))
                .from(qRestaurant)
                .where(qRestaurant.id.eq(placeId))
                .fetchOne();
    }

    @Override
    public CafeResponseDto findCafeByPlaceIdQueryDsl(Long placeId) {
        QCafe qCafe = QCafe.cafe;
        return queryFactory.select(Projections.constructor(CafeResponseDto.class,
                qCafe.id,
                qCafe.name,
                qCafe.phoneNumber,
                qCafe.latitude,
                qCafe.longitude,
                qCafe.thumbNailUrl,
                qCafe.detail,
                qCafe.parkingYn,
                qCafe.address,
                qCafe.img1,
                qCafe.img2,
                qCafe.img3,
                qCafe.img4,
                qCafe.img5,
                qCafe.type,
                qCafe.cardYn,
                qCafe.smokingYn,
                qCafe.dayOff,
                qCafe.playground,
                qCafe.representativeMenu,
                qCafe.reserveInfo,
                qCafe.takeoutYn,
                qCafe.totalMenu,
                qCafe.openTime
                ))
                .from(qCafe)
                .where(qCafe.id.eq(placeId))
                .fetchOne();
    }
}
