package com.cosmos.back.repository.image;

import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.Image;
import com.cosmos.back.model.QImage;
import com.cosmos.back.model.QReview;
import com.cosmos.back.model.QReviewPlace;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    // QueryDSL로 월별 상세 정보 받아오기
    @Override
    public List<ImageResponseDto> findMonthImage(Long coupleId, Long month) {
        QImage qImage = QImage.image;

        return queryFactory.select(Projections.constructor(ImageResponseDto.class,
                qImage.id,
                qImage.imageUrl,
                qImage.review.id,
                qImage.createdTime
                ))
                .from(qImage)
                .where(qImage.createdTime.contains(month.toString()))
                .fetch();
    }

    // QueryDSL로 일별 상세 정보 받아오기
    @Override
    public List<ImageResponseDto> findDayImage(Long coupleId, Long day) {
        QImage qImage = QImage.image;

        return queryFactory.select(Projections.constructor(ImageResponseDto.class,
                qImage.id,
                qImage.imageUrl,
                qImage.review.id,
                qImage.createdTime
                ))
                .from(qImage)
                .where(qImage.createdTime.contains(day.toString()))
                .fetch();
    }

    @Override
    public List<ImageResponseDto> findAllByCoupleId(Long coupleId, Integer limit, Integer offset) {
        QImage qImage = QImage.image;
        QReview qReview = QReview.review;
        QReviewPlace qReviewPlace = QReviewPlace.reviewPlace;

//        return queryFactory.selectFrom(qImage)
//                .leftJoin(qReview)
//                .on(qImage.review.id.eq(qReview.id))
//                .fetchJoin()
//                .leftJoin(qReviewPlace)
//                .on(qReviewPlace.review.id.eq(qReview.id))
//                .where(qImage.coupleId.eq(coupleId))
//                .limit(limit)
//                .offset(offset)
//                .fetch();
        return queryFactory.select(Projections.constructor(ImageResponseDto.class,
                qImage.id,
                qImage.imageUrl,
                qReview.id,
                qImage.createdTime,
                qReviewPlace.place.id
                ))
                .from(qImage)
                .leftJoin(qReview)
                .on(qImage.review.id.eq(qReview.id))
                .fetchJoin()
                .leftJoin(qReviewPlace)
                .on(qReviewPlace.review.id.eq(qReview.id))
                .where(qImage.coupleId.eq(coupleId))
                .limit(limit)
                .offset(offset)
                .fetch()
                ;
    }
}
