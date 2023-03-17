package com.cosmos.back.repository.plan;

import com.cosmos.back.dto.PlanCourseDto;
import com.cosmos.back.model.Plan;

import java.util.List;

public interface PlanRepositoryCustom {

    public List<PlanCourseDto> findByCoupleIdAndMonthQueryDsl(Long coupleId, String yearMonthNext, String yearMonthNow);
    public List<PlanCourseDto> findByCoupleIdAndDayQueryDsl(Long coupleId, String yearMonthDay);
    public Plan findByIdAndCoupleId(Long planId, Long coupleId);
}
