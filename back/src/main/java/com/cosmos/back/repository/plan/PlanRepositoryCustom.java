package com.cosmos.back.repository.plan;

import com.cosmos.back.model.Plan;

import java.util.List;

public interface PlanRepositoryCustom {

    public List<Plan> findByCoupleIdAndMonthQueryDsl(Long coupleId, String yearMonthNext, String yearMonthNow);
    public List<Plan> findByCoupleIdAndDayQueryDsl(Long coupleId, String yearMonthDay);
    //public List<PlanResponseDto> findByPlanIdQueryDsl(Long planId);
}
