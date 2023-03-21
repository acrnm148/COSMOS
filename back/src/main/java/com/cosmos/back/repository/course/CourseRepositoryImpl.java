package com.cosmos.back.repository.course;

import com.cosmos.back.dto.MyCoursePlaceDto;
import com.cosmos.back.model.*;
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
    public List<MyCoursePlaceDto> findAllByUserSeqQueryDSL(Long userSeq) {
        QCourse qCourse = QCourse.course;
        QCoursePlace qCoursePlace = QCoursePlace.coursePlace;
        QPlace qPlace = QPlace.place;

        return queryFactory.select(Projections.constructor(MyCoursePlaceDto.class,
                        qCourse.id,
                        qCourse.name,
                        qCourse.date,
                        qCourse.orders,
                        qPlace.id,
                        qPlace.name,
                        qPlace.phoneNumber,
                        qPlace.latitude,
                        qPlace.longitude,
                        qPlace.thumbNailUrl,
                        qPlace.address
                ))
                .from(qCourse)
                .distinct()
                .where(qCourse.user.userSeq.eq(userSeq))
                .join(qCoursePlace)
                .on(qCourse.id.eq(qCoursePlace.course.id))
                .join(qPlace)
                //.fetchJoin()
                .on(qPlace.id.eq(qCoursePlace.place.id))
                .orderBy( qCourse.id.asc())
                .fetch();
        //return null;

    }

    @Override
    public List<MyCoursePlaceDto> findAllByUserSeqAndCourseIdQueryDSL(Long userSeq, Long courseId) {
        QCourse qCourse = QCourse.course;
        QCoursePlace qCoursePlace = QCoursePlace.coursePlace;
        QPlace qPlace = QPlace.place;

        return queryFactory.select(Projections.constructor(MyCoursePlaceDto.class,
                        qCourse.id,
                        qCourse.name,
                        qCourse.date,
                        qCourse.orders,
                        qPlace.id,
                        qPlace.name,
                        qPlace.phoneNumber,
                        qPlace.latitude,
                        qPlace.longitude,
                        qPlace.thumbNailUrl,
                        qPlace.address
                ))
                .from(qCourse)
                .distinct()
                .where(qCourse.user.userSeq.eq(userSeq)
                        .and(qCourse.id.eq(courseId)))
                .join(qCoursePlace)
                .on(qCourse.id.eq(qCoursePlace.course.id))
                .join(qPlace)
                .on(qPlace.id.eq(qCoursePlace.place.id))
                .orderBy( qCourse.id.asc() )
                .fetch();
        //return null;
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
}