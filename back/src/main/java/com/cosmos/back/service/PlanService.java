package com.cosmos.back.service;

import com.cosmos.back.dto.request.PlanRequestDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.Plan;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
    public Long createPlan(PlanRequestDto dto) {
        Plan plan = Plan.builder()
                .coupleId(dto.getCoupleId())
                .mainCategory(dto.getMainCategory())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .createTime(now())
                .build();

        List<Long> courseIds = dto.getCourseIds();
        List<Course> courses = new ArrayList<>();
        if (courseIds != null) {
            for (Long id : courseIds) {
                Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
                course.setPlan(plan); //course에 planId 추가
                courseRepository.save(course);
                courses.add(course);
            }
        }
        //plan.setCourses(courses);
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
    public Plan updatePlan(PlanRequestDto dto) {
        Plan plan = planRepository.findByIdAndCoupleId(dto.getPlanId(), dto.getCoupleId());
        System.out.println("courses: ");
        List<Long> courseIds = dto.getCourseIds();
        List<Course> courses = new ArrayList<>();
        if (courseIds != null) {
            for (Long id : courseIds) {
                Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
                course.setPlan(plan); //course에 planId 수정
                courseRepository.save(course);
                courses.add(course);
            }
        }
        //plan.setCourses(courses);
        plan.setMainCategory(dto.getMainCategory());
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
        planRepository.save(plan);
    }

    /**
     * 커플 일정 월별 조회
     * where date_format(create_time, '%Y')='2023' and date_format(create_time, '%m')='03';
     */
    public List<Plan> getPlanListByMonth(Long coupleId, String date) {
        String year = date.substring(0,4);
        String month = date.substring(5,7);

        String YearMonth = year+"-"+month;

        List<Plan> plansByMonth= planRepository.findByCoupleIdAndMonthQueryDsl(coupleId, YearMonth);

        return plansByMonth;
    }

    /**
     * 커플 일정 일별 조회
     * where date(create_time)='2023-03-05';
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
