package com.cosmos.back.repository.course;

import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.model.*;
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
    public CourseResponseDto findByCourseIdQueryDSL(Long courseId) {
        QCourse qCourse = QCourse.course;

        return queryFactory.select(Projections.constructor(CourseResponseDto.class,
                        qCourse.name,
                        qCourse.date,
                        qCourse.id
                ))
                .from(qCourse)
                .where(qCourse.id.eq(courseId))
                .fetchOne();
    }

    @Override
    public Long deleteCoursePlaceQueryDSL(Long courseId, CoursePlace cp) {

        QCourse course = QCourse.course;
        QCoursePlace coursePlace = QCoursePlace.coursePlace;

        long execute = queryFactory.delete(coursePlace)
                .where(coursePlace.id.eq(cp.getId()))
                .execute();

        return execute;

    }

    // 내 찜한 코스 보기
    @Override
    public List<CourseResponseDto> findAllCourseByUserSeq(Long userSeq) {
        QCourse qCourse = QCourse.course;

        return queryFactory.select(Projections.constructor(CourseResponseDto.class,
                    qCourse.name,
                    qCourse.date,
                    qCourse.id,
                    qCourse.orders
                ))
                .from(qCourse)
                .where(qCourse.user.userSeq.eq(userSeq).and(qCourse.wish.eq(true)))
                .fetch();
    }
}