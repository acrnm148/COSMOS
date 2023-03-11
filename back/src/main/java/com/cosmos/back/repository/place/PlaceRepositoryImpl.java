package com.cosmos.back.repository.place;

import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.QReview;
import com.cosmos.back.model.QReviewPlace;
import com.cosmos.back.model.place.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                    qTour.dayOff,
                    qTour.program
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
                qAccommodation.type,
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
                qAccommodation.acceptablePeople,
                qAccommodation.roomNum,
                qAccommodation.roomType,
                qAccommodation.cookYn,
                qAccommodation.checkIn,
                qAccommodation.checkOut,
                qAccommodation.reservationPage,
                qAccommodation.pickupYn,
                qAccommodation.karaokeYn,
                qAccommodation.bbqYn,
                qAccommodation.gymYn,
                qAccommodation.refund
                ))
                .from(qAccommodation)
                .where(qAccommodation.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 음식점 상세 정보 받아오기
    @Override
    public RestaurantResponseDto findRestaurantByPlaceIdQueryDsl(Long placeId) {
        QRestaurant qRestaurant = QRestaurant.restaurant;
        return queryFactory.select(Projections.constructor(RestaurantResponseDto.class,
                        qRestaurant.id,
                        qRestaurant.type,
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
                        qRestaurant.playground,
                        qRestaurant.dayOff,
                        qRestaurant.representativeMenu,
                        qRestaurant.totalMenu,
                        qRestaurant.smokingYn,
                        qRestaurant.cardYn,
                        qRestaurant.takeoutYn,
                        qRestaurant.reserveInfo,
                        qRestaurant.openTime
                ))
                .from(qRestaurant)
                .where(qRestaurant.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 카페 상세 정보 받아오기
    @Override
    public CafeResponseDto findCafeByPlaceIdQueryDsl(Long placeId) {
        QCafe qCafe = QCafe.cafe;
        return queryFactory.select(Projections.constructor(CafeResponseDto.class,
                qCafe.id,
                qCafe.type,
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
                qCafe.playground,
                qCafe.dayOff,
                qCafe.representativeMenu,
                qCafe.totalMenu,
                qCafe.smokingYn,
                qCafe.cardYn,
                qCafe.takeoutYn,
                qCafe.reserveInfo,
                qCafe.openTime
                ))
                .from(qCafe)
                .where(qCafe.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 쇼핑 상세 정보 받아오기
    @Override
    public ShoppingResponseDto findShoppingByPlaceIdQueryDsl(Long placeId) {
        QShopping qShopping = QShopping.shopping;
        return queryFactory.select(Projections.constructor(ShoppingResponseDto.class,
                qShopping.id,
                qShopping.name,
                qShopping.phoneNumber,
                qShopping.latitude,
                qShopping.longitude,
                qShopping.thumbNailUrl,
                qShopping.detail,
                qShopping.parkingYn,
                qShopping.address,
                qShopping.img1,
                qShopping.img2,
                qShopping.img3,
                qShopping.img4,
                qShopping.img5,
                qShopping.type,
                qShopping.shoppingList,
                qShopping.dayOff,
                qShopping.strollerYn,
                qShopping.petYn,
                qShopping.cardYn,
                qShopping.openTime,
                qShopping.openDay
                ))
                .from(qShopping)
                .where(qShopping.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 레포츠 상세 정보 받아오기
    @Override
    public LeisureResponseDto findLeisureByPlaceIdQueryDsl(Long placeId) {
        QLeisure qLeisure = QLeisure.leisure;
        return queryFactory.select(Projections.constructor(LeisureResponseDto.class,
                qLeisure.id,
                qLeisure.name,
                qLeisure.phoneNumber,
                qLeisure.latitude,
                qLeisure.longitude,
                qLeisure.thumbNailUrl,
                qLeisure.detail,
                qLeisure.parkingYn,
                qLeisure.address,
                qLeisure.img1,
                qLeisure.img2,
                qLeisure.img3,
                qLeisure.img4,
                qLeisure.img5,
                qLeisure.type,
                qLeisure.acceptablePeople,
                qLeisure.dayOff,
                qLeisure.parkingCost,
                qLeisure.openTime,
                qLeisure.openPeriod,
                qLeisure.price,
                qLeisure.ageRange,
                qLeisure.petYn
                ))
                .from(qLeisure)
                .where(qLeisure.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 문화시설 상세 정보 받아오기
    @Override
    public CultureResponseDto findCultureByPlaceIdQueryDsl(Long placeId) {
        QCulture qCulture = QCulture.culture;
        return queryFactory.select(Projections.constructor(CultureResponseDto.class,
                qCulture.id,
                qCulture.name,
                qCulture.phoneNumber,
                qCulture.latitude,
                qCulture.longitude,
                qCulture.thumbNailUrl,
                qCulture.detail,
                qCulture.parkingYn,
                qCulture.address,
                qCulture.img1,
                qCulture.img2,
                qCulture.img3,
                qCulture.img4,
                qCulture.img5,
                qCulture.type,
                qCulture.dayOff,
                qCulture.petYn
                ))
                .from(qCulture)
                .where(qCulture.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 장소 리스트 가져오기 with Pagination
    @Override
    public List<PlaceListResponseDto> findPlaceListByNameQueryDsl(String name, Integer limit, Integer offset) {
        QPlace qPlace = QPlace.place;
        QReview qReview = QReview.review;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;

        return queryFactory.select(Projections.constructor(PlaceListResponseDto.class,
                    qPlace.id,
                    qPlace.name,
                    qPlace.address,
                    qReview.score.avg(),
                    qPlace.thumbNailUrl,
                    qPlace.detail
                ))
                .from(qPlace)
                .join(qReviewPlace)
                .on(qPlace.id.eq(qReviewPlace.place.id))
                .leftJoin(qReview)
                .fetchJoin()
                .on(qReviewPlace.review.id.eq(qReview.id))
                .where(qPlace.name.eq(name))
                .groupBy(qPlace.id)
                .limit(limit)
                .offset(offset)
                .fetch();
    }
}
