package com.cosmos.back.repository.plan;

import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.Plan;

import java.util.List;

public interface PlanRepositoryCustom {

    public List<Plan> findByCoupleIdAndMonthQueryDsl(Long coupleId, String yearMonth);
    public List<Plan> findByCoupleIdAndDayQueryDsl(Long coupleId, String yearMonthDay);
}
