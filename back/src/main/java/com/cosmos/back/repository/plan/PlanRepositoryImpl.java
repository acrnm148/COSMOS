package com.cosmos.back.repository.plan;

import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.Plan;
import com.cosmos.back.model.QPlan;
import com.cosmos.back.model.place.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * SELECT * FROM plan
     * where start_date <= '2023-03' and end_date >= '2023-03';
     * lt <, gt >
     * loe <=, goe >=
     */
    @Override
    public List<Plan> findByCoupleIdAndMonthQueryDsl(Long coupleId, String yearMonth) {
        QPlan qPlan = QPlan.plan;

        List<Plan> result = queryFactory.selectFrom(qPlan)
                .where(qPlan.coupleId.eq(coupleId)
                        .and(qPlan.startDate.loe(yearMonth))
                        .and(qPlan.endDate.goe(yearMonth)))
                .fetch();
        return result;
    }

    /**
     * SELECT * FROM cosmos.plan
     * where start_date <= '2023-03-01' or end_date >= '2023-03-01';
     * lt <, gt >
     * loe <=, goe >=
     */
    @Override
    public List<Plan> findByCoupleIdAndDayQueryDsl(Long coupleId, String yearMonthDay) {
        QPlan qPlan = QPlan.plan;

        List<Plan> result = queryFactory.selectFrom(qPlan)
                .where(qPlan.coupleId.eq(coupleId)
                        .and(qPlan.startDate.loe(yearMonthDay))
                        .and(qPlan.endDate.goe(yearMonthDay)))
                .fetch();
        return result;
    }
}
