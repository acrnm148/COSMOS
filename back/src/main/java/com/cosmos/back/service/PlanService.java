package com.cosmos.back.service;

import com.cosmos.back.dto.PlanDto;
import com.cosmos.back.dto.PlanCourseDto;
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
    public Plan createPlan(PlanDto dto) {
        List<Course> newCourses = new ArrayList<> ();
        List<Long> ids = dto.getCourseIds();
        for (Long id : ids) {
            Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
            newCourses.add(course);
        }

        Plan plan = Plan.builder()
                .coupleId(dto.getCoupleId())
                .planName(dto.getPlanName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .courses(newCourses)
                .createTime(now())
                .build();
        planRepository.save(plan);

        System.out.println("커플 일정 생성 완료");
        return plan;
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
        System.out.println("courses: "+dto.getCourses());

        List<Long> courseIds = dto.getCourseIds();
        List<Course> courses = new ArrayList<> ();
        for (Long id: courseIds) {
            Course course = courseRepository.findById(id).orElseThrow(()->new IllegalArgumentException("no such data"));
            courses.add(course);
        }
        plan.setCourses(courses);
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
    public PlanDto getPlanListByMonth(Long coupleId, String date) {
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

        List<PlanCourseDto> plansByMonth= planRepository.findByCoupleIdAndMonthQueryDsl(coupleId, YearMonthNext,YearMonthNow);
        List<Course> courses = new ArrayList<> ();
        PlanDto result = new PlanDto();
        Long lastPlanId = 0L;
        for (PlanCourseDto planCourse : plansByMonth) {
            if (!(lastPlanId.equals(planCourse.getPlanId()))) {
                lastPlanId = planCourse.getPlanId();
                result.setPlanId(planCourse.getPlanId());
                result.setCoupleId(planCourse.getCoupleId());
                result.setPlanName(planCourse.getPlanName());
                result.setStartDate(planCourse.getStartDate());
                result.setEndDate(planCourse.getEndDate());
                result.setCreateTime(planCourse.getCreateTime());
                result.setUpdateTime(planCourse.getUpdateTime());
            }
            if (planCourse.getId() != null) {
                courses.add(Course.builder()
                        .id(planCourse.getId())
                        .name(planCourse.getName())
                        .date(planCourse.getDate())
                        .subCategory(planCourse.getSubCategory())
                        .idx(planCourse.getIdx())
                        .build());
            }
        }
        result.setCourses(courses);

        if (result.getPlanId() == null) {
            return null;
        }
        return result;
    }

    /**
     * 커플 일정 일별 조회 day
     */
    public PlanDto getPlanListByDay(Long coupleId, String date) {
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);

        String YearMonthDay = year+"-"+month+"-"+day;

        List<PlanCourseDto> plansByDay = planRepository.findByCoupleIdAndDayQueryDsl(coupleId, YearMonthDay);
        List<Course> courses = new ArrayList<> ();
        PlanDto result = new PlanDto();
        Long lastPlanId = 0L;
        for (PlanCourseDto planCourse : plansByDay) {
            if (!(lastPlanId.equals(planCourse.getPlanId()))) {
                lastPlanId = planCourse.getPlanId();
                result.setPlanId(planCourse.getPlanId());
                result.setCoupleId(planCourse.getCoupleId());
                result.setPlanName(planCourse.getPlanName());
                result.setStartDate(planCourse.getStartDate());
                result.setEndDate(planCourse.getEndDate());
                result.setCreateTime(planCourse.getCreateTime());
                result.setUpdateTime(planCourse.getUpdateTime());
            }
            if (planCourse.getId() != null) {
                courses.add(Course.builder()
                        .id(planCourse.getId())
                        .name(planCourse.getName())
                        .date(planCourse.getDate())
                        .subCategory(planCourse.getSubCategory())
                        .idx(planCourse.getIdx())
                        .build());
            }
        }
        result.setCourses(courses);
        if (result.getPlanId() == null) {
            return null;
        }
        return result;
    }
}
