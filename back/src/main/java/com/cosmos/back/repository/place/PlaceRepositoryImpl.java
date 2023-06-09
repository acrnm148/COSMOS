package com.cosmos.back.repository.place;

import com.cosmos.back.dto.SimplePlaceDto;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.*;
import com.cosmos.back.model.place.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // QueryDsl로 관광 상세 정보 받아오기
    @Override
    public TourResponseDto findTourByPlaceIdQueryDsl(Long placeId) {
        QTour qTour = QTour.tour;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;

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
                    qTour.program,
                    qReview.score.avg()
                ))
                .from(qTour)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.place.id.eq(qTour.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(qTour.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 축제 상세 정보 받아오기
    @Override
    public FestivalResponseDto findFestivalByPlaceIdQueryDsl(Long placeId) {
        QFestival qFestival = QFestival.festival;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;

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
                        qFestival.takenTime,
                        qReview.score.avg()
                ))
                .from(qFestival)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.place.id.eq(qFestival.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(qFestival.id.eq(placeId).and(qFestival.startDate.gt("20230322")))
                .fetchOne();
    }

    // QueryDsl로 숙박 상세 정보 받아오기
    @Override
    public AccommodationResponseDto findAccommodationByPlaceIdQueryDsl(Long placeId) {
        QAccommodation qAccommodation = QAccommodation.accommodation;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;

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
                qAccommodation.refund,
                qReview.score.avg()
                ))
                .from(qAccommodation)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.place.id.eq(qAccommodation.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(qAccommodation.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 음식점 상세 정보 받아오기
    @Override
    public RestaurantResponseDto findRestaurantByPlaceIdQueryDsl(Long placeId) {
        QRestaurant qRestaurant = QRestaurant.restaurant;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;

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
                        qRestaurant.openTime,
                        qReview.score.avg()
                ))
                .from(qRestaurant)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.place.id.eq(qRestaurant.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(qRestaurant.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 카페 상세 정보 받아오기
    @Override
    public CafeResponseDto findCafeByPlaceIdQueryDsl(Long placeId) {
        QCafe qCafe = QCafe.cafe;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;

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
                qCafe.openTime,
                qReview.score.avg()
                ))
                .from(qCafe)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.place.id.eq(qCafe.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(qCafe.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 쇼핑 상세 정보 받아오기
    @Override
    public ShoppingResponseDto findShoppingByPlaceIdQueryDsl(Long placeId) {
        QShopping qShopping = QShopping.shopping;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;

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
                qShopping.openDay,
                qReview.score.avg()
                ))
                .from(qShopping)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.place.id.eq(qShopping.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(qShopping.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 레포츠 상세 정보 받아오기
    @Override
    public LeisureResponseDto findLeisureByPlaceIdQueryDsl(Long placeId) {
        QLeisure qLeisure = QLeisure.leisure;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;

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
                qLeisure.petYn,
                qReview.score.avg()
                ))
                .from(qLeisure)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.place.id.eq(qLeisure.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(qLeisure.id.eq(placeId))
                .fetchOne();
    }

    // QueryDsl로 문화시설 상세 정보 받아오기
    @Override
    public CultureResponseDto findCultureByPlaceIdQueryDsl(Long placeId) {
        QCulture qCulture = QCulture.culture;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;
        QReview qReview = QReview.review;

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
                qCulture.petYn,
                qReview.score.avg()
                ))
                .from(qCulture)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.place.id.eq(qCulture.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(qCulture.id.eq(placeId))
                .fetchOne();
    }


    // 장소 찜 확인하기
    @Override
    public boolean findPlaceLikeByPlaceIdUserSeqQueryDsl (Long placeId, Long userSeq) {
        QUserPlace qUserPlace = QUserPlace.userPlace;
        Boolean result;

        Integer like = queryFactory
                .selectOne()
                .from(qUserPlace)
                .where(qUserPlace.user.userSeq.eq(userSeq).and(qUserPlace.place.id.eq(placeId)))
                .fetchFirst();

        if (like == null) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    // 시/도, 구/군, 검색어, 검색필터를 통한 장소 검색
    @Override
    public List<PlaceSearchListResponseDto> findPlaceListBySidoGugunTextFilterQueryDsl(Long userSeq, String sido, String gugun, String text, String filter) {
        QPlace qPlace = QPlace.place;
        QReview qReview = QReview.review;
        QFestival qFestival = QFestival.festival;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;

        BooleanBuilder builder = new BooleanBuilder();

        if (sido != null) {
            builder.and(qPlace.address.contains(sido));
        }
        if (gugun != null) {
            builder.and(qPlace.address.contains(gugun));
        }
        if (text != null) {
            builder.and(qPlace.name.contains(text));
        }
        if (filter != null) {
            if (filter.equals("festival")) {
                builder.and(qPlace.type.contains(filter));
                builder.and(qFestival.startDate.gt("20230322"));
                return queryFactory.select(Projections.constructor(PlaceSearchListResponseDto.class,
                                qPlace.id,
                                qPlace.name,
                                qPlace.address,
                                qReview.score.avg(),
                                qPlace.thumbNailUrl,
                                qPlace.detail,
                                qPlace.latitude,
                                qPlace.longitude,
                                qPlace.type
                        ))
                        .from(qPlace)
                        .leftJoin(qReviewPlace)
                        .on(qReviewPlace.review.id.eq(qPlace.id))
                        .leftJoin(qReview)
                        .on(qReview.id.eq(qReviewPlace.review.id))
                        .fetchJoin()
                        .leftJoin(qFestival)
                        .on(qPlace.id.eq(qFestival.id))
                        .where(builder)
                        .groupBy(qPlace.id)
                        .fetch();
            } else {
                builder.and(qPlace.type.contains(filter));
            }
        }
        return queryFactory.select(Projections.constructor(PlaceSearchListResponseDto.class,
                    qPlace.id,
                    qPlace.name,
                    qPlace.address,
                    qReview.score.avg(),
                    qPlace.thumbNailUrl,
                    qPlace.detail,
                    qPlace.latitude,
                    qPlace.longitude,
                    qPlace.type
                ))
                .from(qPlace)
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.review.id.eq(qPlace.id))
                .leftJoin(qReview)
                .on(qReview.id.eq(qReviewPlace.review.id))
                .fetchJoin()
                .where(builder)
                .groupBy(qPlace.id)
                .fetch();
    }

    // QueryDsl로 장소 검색 자동 완성 (Limit = 10)
    @Override
    public List<AutoCompleteResponseDto> findPlaceListByNameAutoCompleteQueryDsl(String searchWord) {
        QPlace qPlace = QPlace.place;

        return queryFactory.select(Projections.constructor(AutoCompleteResponseDto.class,
                        qPlace.id,
                        qPlace.name,
                        qPlace.address,
                        qPlace.thumbNailUrl,
                        qPlace.type
                ))
                .from(qPlace)
                .where(qPlace.name.contains(searchWord))
                .limit(10)
                .fetch();
    }

    // QueryDsl로 장소별 별점 평점 가져오기
    @Override
    public Double findScoreByPlaceIdQueryDsl(Long placeId) {
        QPlace qPlace = QPlace.place;
        QReview qReview = QReview.review;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;

        return queryFactory.select(qReview.score.avg())
                .from(qPlace)
                .leftJoin(qReviewPlace)
                .fetchJoin()
                .on(qPlace.id.eq(qReviewPlace.place.id))
                .leftJoin(qReview)
                .fetchJoin()
                .on(qReviewPlace.review.id.eq(qReview.id))
                .where(qPlace.id.eq(placeId))
                .groupBy(qPlace.id)
                .fetchOne();
    }

    // QueryDsl로 시도, 구군, 타입별, 경향별 장소 리스트 가져오기
    @Override
    public List<Place> findAllByTypeAndSidoAndGugunT(String type, String sido, String gugun, String t) {
        QPlace qPlace = QPlace.place;

        StringBuilder sb = new StringBuilder();
        sb.append("%");
        for (int i = 0; i < t.length(); i++) {
            sb.append(t.charAt(i));
            sb.append("%");
        }

        String tendency = sb.toString();
        return queryFactory.selectFrom(qPlace)
                .where(qPlace.type.eq(type)
                        .and(qPlace.address.contains(sido)
                                .and(qPlace.address.contains(gugun))
                                .and(qPlace.tendency.like(tendency))))
                .fetch();
    }

    // QueryDsl로 시도, 구군, 타입별 장소 리스트 가져오기
    @Override
    public List<Place> findAllByTypeAndSidoAndGugun(String type, String sido, String gugun) {
        QPlace qPlace = QPlace.place;

        return queryFactory.selectFrom(qPlace)
                .where(qPlace.type.eq(type)
                        .and(qPlace.address.contains(sido)
                                .and(qPlace.address.contains(gugun))))
                .fetch();
    }

    // QueryDsl로 SimplePlaceDto 내용 가져오기
    @Override
    public SimplePlaceDto findSimplePlaceDtoByPlaceIdQueryDsl(Long placeId, Long courseId) {
        QPlace qPlace = QPlace.place;
        QCoursePlace qCoursePlace = QCoursePlace.coursePlace;

        return queryFactory.select(Projections.constructor(SimplePlaceDto.class,
                    qCoursePlace.course.id,
                    qPlace.id,
                    qPlace.name,
                    qPlace.latitude,
                    qPlace.longitude,
                    qPlace.thumbNailUrl,
                    qPlace.address,
                    qPlace.detail,
                    qCoursePlace.orders,
                    qPlace.phoneNumber,
                    qPlace.type
                ))
                .from(qPlace)
                .leftJoin(qCoursePlace)
                .fetchJoin()
                .on(qPlace.id.eq(qCoursePlace.place.id).and(qCoursePlace.course.id.eq(courseId)))
                .where(qPlace.id.eq(placeId))
                .fetchOne();
    }

    @Override
    public List<SimplePlaceDto> findSimplePlaceDtoByCourseIdQueryDsl(Long courseId) {
        QPlace qPlace = QPlace.place;
        QCoursePlace qCoursePlace = QCoursePlace.coursePlace;

        return queryFactory.select(Projections.constructor(SimplePlaceDto.class,
                        qCoursePlace.course.id,
                        qPlace.id,
                        qPlace.name,
                        qPlace.latitude,
                        qPlace.longitude,
                        qPlace.thumbNailUrl,
                        qPlace.address,
                        qPlace.detail,
                        qCoursePlace.orders,
                        qPlace.phoneNumber,
                        qPlace.type
                ))
                .from(qPlace)
                .leftJoin(qCoursePlace)
                .fetchJoin()
                .on(qPlace.id.eq(qCoursePlace.place.id))
                .where(qCoursePlace.course.id.eq(courseId))
                .orderBy(qCoursePlace.orders.asc())
                .fetch();
    }
}
