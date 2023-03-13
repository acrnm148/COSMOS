package com.cosmos.back.repository.course;

import com.cosmos.back.dto.CourseDto;
import com.cosmos.back.dto.PlanCourseDto;
import com.cosmos.back.dto.response.place.PlaceListResponseDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.Plan;
import com.cosmos.back.model.QCourse;
import com.cosmos.back.model.QPlan;
import com.cosmos.back.model.place.QPlace;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CourseDto> findAllByUserSeqQueryDSL(Long userSeq) {
        QCourse qCourse = QCourse.course;
        QPlace qPlace = QPlace.place;

        /*
        List<CourseDto> result = queryFactory.select(Projections.constructor(CourseDto.class,
                        qCourse.id,
                        qCourse.name,
                        qCourse.date,
                        qCourse.subCategory,
                        qCourse.orders
                ))
                .from(qCourse)
                .where(qCourse.user.userSeq.eq(userSeq))
                .fetch();

        List<PlaceListResponseDto> places = queryFactory.select(Projections.constructor(PlaceListResponseDto.class,
                        qPlace.id,
                        qPlace.name,
                        qPlace.address,
                        qPlace.thumbNailUrl,
                        qPlace.detail
                ))
                .from(qPlace)
                .where(qCourse.user.userSeq.eq(userSeq))
                .fetch();

        return result;
        */
        return null;
    }

    @Override
    public CourseDto findAllByUserSeqAndCourseIdQueryDSL(Long userSeq, Long courseId) {
        QCourse qCourse = QCourse.course;

        CourseDto result = queryFactory.select(Projections.constructor(CourseDto.class,
                        qCourse.id,
                        qCourse.name,
                        qCourse.date,
                        qCourse.subCategory,
                        qCourse.orders
                ))
                .from(qCourse)
                .where(qCourse.user.userSeq.eq(userSeq)
                        .and(qCourse.id.eq(courseId)))
                .fetchOne();
        return result;
    }
}
