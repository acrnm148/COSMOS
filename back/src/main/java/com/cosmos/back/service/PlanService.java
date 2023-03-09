package com.cosmos.back.service;

import com.cosmos.back.dto.request.PlanRequestDto;
import com.cosmos.back.model.Plan;
import com.cosmos.back.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final PlanRepository planRepository;

    /**
     * 커플 일정 생성
     */
    public Long createPlan(PlanRequestDto dto) {
        Plan plan = Plan.builder()
                .coupleId(dto.getCoupleId())
                .mainCategory(dto.getMainCategory())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .courses(dto.getCourses())
                .build();
        planRepository.save(plan);
        System.out.println("커플 일정 생성 완료");
        return plan.getId();
    }

    /**
     * 커플 일정 상세보기
     */
    public Plan getPlanDetail(Long coupleId, Long planId) {
        Plan plan = planRepository.findByIdAndCoupleId(planId, coupleId);
        System.out.println("조회된 일정: "+plan);
        return plan;
    }

    /**
     * 커플 일정 삭제
     */
    public void deletePlan(Long coupleId, Long planId) {
        Plan plan = planRepository.findByIdAndCoupleId(planId, coupleId);
        planRepository.save(plan);
    }

    /**
     * 커플 일정 월별 조회
     */
    public List<Plan> getPlanListByMonth(Long coupleId) {

        return null;
    }

    /**
     * 커플 일정 일별 조회
     */
    public List<PlanOfDayDto> getPlanListByDay(Long coupleId) {

        return null;
    }
}
