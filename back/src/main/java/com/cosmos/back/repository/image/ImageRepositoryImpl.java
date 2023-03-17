package com.cosmos.back.repository.image;

import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.QImage;
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
                qImage.imageUrl
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
                qImage.imageUrl
                ))
                .from(qImage)
                .where(qImage.createdTime.contains(day.toString()))
                .fetch();
    }
}
