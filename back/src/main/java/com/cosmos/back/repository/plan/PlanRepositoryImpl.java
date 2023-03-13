package com.cosmos.back.repository.plan;

import com.cosmos.back.model.Plan;
import com.cosmos.back.model.QPlan;
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
    public List<Plan> findByCoupleIdAndMonthQueryDsl(Long coupleId, String yearMonthNext, String yearMonthNow) {
        QPlan qPlan = QPlan.plan;

        List<Plan> result = queryFactory.selectFrom(qPlan)
                .where(qPlan.coupleId.eq(coupleId)
                        .and(qPlan.startDate.lt(yearMonthNext))
                        .and(qPlan.endDate.goe(yearMonthNow)))
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
