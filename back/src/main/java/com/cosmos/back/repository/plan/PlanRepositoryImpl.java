package com.cosmos.back.repository.plan;

import com.cosmos.back.dto.PlanCourseDto;
import com.cosmos.back.model.Plan;
import com.cosmos.back.model.QCourse;
import com.cosmos.back.model.QPlan;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 월별조회
     * SELECT * FROM plan
     * WHERE plan.couple_id = 132739941
     * AND start_date < '2023-04-01' AND end_date >= '2023-03-01';
     * ---
     * lt <, gt >
     * loe <=, goe >=
     */
    @Override
    public List<PlanCourseDto> findByCoupleIdAndMonthQueryDsl(Long coupleId, String yearMonthNext, String yearMonthNow) {
        QPlan qPlan = QPlan.plan;
        QCourse qCourse = QCourse.course;

        List<PlanCourseDto> result = queryFactory.select(Projections.constructor(PlanCourseDto.class,
                            qPlan.id,
                            qPlan.coupleId,
                            qPlan.planName,
                            qPlan.startDate,
                            qPlan.endDate,
                            qPlan.createTime,
                            qPlan.updateTime,
                            qCourse.id,
                            qCourse.name,
                            qCourse.date,
                            qCourse.subCategory,
                            qCourse.orders
                ))
                .from(qPlan)
                .leftJoin(qPlan.courses, qCourse)
                .where(qPlan.coupleId.eq(coupleId)
                        .and(qPlan.startDate.lt(yearMonthNext))
                        .and(qPlan.endDate.goe(yearMonthNow))
                        .and(qCourse.date.like(yearMonthNow+"%")))
                .orderBy(qPlan.id.asc(), qCourse.orders.asc())
                .fetch();
        return result;
    }

    /**
     * 일별조회
     * SELECT * FROM cosmos.plan
     * WHERE plan.couple_id = 132739941
     * AND start_date <= '2023-03-21' AND end_date >= '2023-03-21';
     * ---
     * lt <, gt >
     * loe <=, goe >=
     */
    @Override
    public List<PlanCourseDto> findByCoupleIdAndDayQueryDsl(Long coupleId, String yearMonthDay) {
        QPlan qPlan = QPlan.plan;
        QCourse qCourse = QCourse.course;

        List<PlanCourseDto> result = queryFactory.select(Projections.constructor(PlanCourseDto.class,
                        qPlan.id,
                        qPlan.coupleId,
                        qPlan.planName,
                        qPlan.startDate,
                        qPlan.endDate,
                        qPlan.createTime,
                        qPlan.updateTime,
                        qCourse.id,
                        qCourse.name,
                        qCourse.date,
                        qCourse.subCategory,
                        qCourse.orders
                ))
                .from(qPlan)
                .leftJoin(qPlan.courses, qCourse)
                .on(qCourse.date.like(yearMonthDay+"%"))
                .where(qPlan.coupleId.eq(coupleId)
                        .and(qPlan.startDate.loe(yearMonthDay))
                        .and(qPlan.endDate.goe(yearMonthDay)))
                .orderBy(qPlan.id.asc(), qCourse.orders.asc())
                .fetch();
        return result;
    }

    @Override
    public Plan findByIdAndCoupleId(Long planId, Long coupleId) {
        QPlan qPlan = QPlan.plan;
        QCourse qCourse = QCourse.course;
        Plan result = queryFactory.selectFrom(qPlan)
                .leftJoin(qPlan.courses, qCourse)
                .where(qPlan.id.eq(planId)
                        .and(qPlan.coupleId.eq(coupleId)))
                .orderBy(qPlan.id.asc(), qCourse.orders.asc())
                .fetchOne();

        return result;
    }
}
