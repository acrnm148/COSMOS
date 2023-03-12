package com.cosmos.back.service;

import com.cosmos.back.dto.PlanDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.Plan;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final PlanRepository planRepository;
    private final CourseRepository courseRepository;

    /**
     * 커플 일정 생성
     */
    public Long createPlan(PlanDto dto) {
        Plan plan = Plan.builder()
                .coupleId(dto.getCoupleId())
                .planName(dto.getPlanName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .courses(dto.getCourses())
                .createTime(now())
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
     * 커플 일정 수정
     */
    public Plan updatePlan(PlanDto dto) {
        Plan plan = planRepository.findByIdAndCoupleId(dto.getPlanId(), dto.getCoupleId());
        System.out.println("courses: ");
        plan.setCourses(dto.getCourses());
        plan.setPlanName(dto.getPlanName());
        plan.setStartDate(dto.getStartDate());
        plan.setEndDate(dto.getEndDate());
        plan.setUpdateTime(now());
        planRepository.save(plan);
        System.out.println("커플 일정 생성 완료");
        return plan;
    }

    /**
     * 커플 일정 삭제
     */
    public void deletePlan(Long coupleId, Long planId) {
        Plan plan = planRepository.findByIdAndCoupleId(planId, coupleId);
        System.out.println("커플 일정 삭제 완료, id:"+planId);
        planRepository.save(plan);
    }

    /**
     * 커플 일정 월별 조회 month
     */
    public List<Plan> getPlanListByMonth(Long coupleId, String date) {
        String year = date.substring(0,4);
        String month = date.substring(5,7);

        String YearMonthNow = year+"-"+month;

        String newYear = "";
        String newMonth = "";
        if (month.equals("12")) {
            newYear += Integer.parseInt(year)+1;
            newMonth += "01";
        } else {
            newYear += year;
            Integer tmp = Integer.parseInt(month)+1;
            if (tmp < 10)
            newMonth += "0"+tmp;
        }
        String YearMonthNext = newYear+"-"+newMonth;

        List<Plan> plansByMonth= planRepository.findByCoupleIdAndMonthQueryDsl(coupleId, YearMonthNext,YearMonthNow);

        return plansByMonth;
    }

    /**
     * 커플 일정 일별 조회 day
     */
    public List<Plan> getPlanListByDay(Long coupleId, String date) {
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);

        String YearMonthDay = year+"-"+month+"-"+day;

        List<Plan> plansByDay = planRepository.findByCoupleIdAndDayQueryDsl(coupleId, YearMonthDay);

        return plansByDay;
    }
}
