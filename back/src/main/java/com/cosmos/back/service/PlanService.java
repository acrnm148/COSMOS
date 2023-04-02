package com.cosmos.back.service;

import com.cosmos.back.dto.*;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.CoursePlace;
import com.cosmos.back.model.Plan;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final PlaceRepository placeRepository;
    private final PlanRepository planRepository;
    private final CourseRepository courseRepository;
    private final CoursePlaceRepository coursePlaceRepository;

    /**
     * 커플 일정 생성
     */
    public Plan createPlan(PlanDto dto) {
        Plan plan = Plan.builder()
                .coupleId(dto.getCoupleId())
                .planName(dto.getPlanName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                //.courses(newCourses)
                .createTime(now())
                .build();
        List<Course> newCourses = new ArrayList<> ();
        List<CourseIdAndDate> courseIdAndDateList = dto.getCourseIdAndDateList();
        int len = courseIdAndDateList.size();

        int order = 1;
        for (int i=0; i<len; i++) {
            Long id = courseIdAndDateList.get(i).getCourseId();
            String date = courseIdAndDateList.get(i).getDate();

            Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
            course.setOrders(order++);
            course.setPlan(plan);
            course.setDate(date);
            newCourses.add(course);
        }

        plan.setCourses(newCourses);
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

        //List<Long> courseIds = dto.getCourseIds();
        List<CourseIdAndDate> courseIdAndDateList = dto.getCourseIdAndDateList();
        int len = courseIdAndDateList.size();
        List<Course> courses = new ArrayList<> ();
        for (int i=0; i<len; i++) {
            Long id = courseIdAndDateList.get(i).getCourseId();
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
        planRepository.delete(plan);
    }

    /**
     * 커플 일정 월별 조회 month
     */
    public List<PlanDto> getPlanListByMonth(Long coupleId, String date) {
        System.out.println("일정 월별 조회");
        String year = date.substring(0,4);
        String month = date.substring(4,6);

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
        
        System.out.println(YearMonthNow+"~"+YearMonthNext+" 일정 조회");

        List<PlanCourseDto> plansByMonth= planRepository.findByCoupleIdAndMonthQueryDsl(coupleId, YearMonthNext,YearMonthNow);
        List<SimpleCourseDto> courses = new ArrayList<> ();
        PlanDto result = new PlanDto();
        List<PlanDto> resultList = new ArrayList<>();
        Long lastPlanId = 0L;
        int len = plansByMonth.size();

        System.out.println("len:"+len);
        System.out.println("plansByMonth:"+plansByMonth);

        for (int i=0; i<len; i++) {
            PlanCourseDto planCourse = plansByMonth.get(i);
            System.out.println(planCourse);

            if (planCourse.getId() != null) {
                List<SimplePlaceDto> places = placeRepository.findSimplePlaceDtoByCourseIdQueryDsl(planCourse.getId());

                courses.add(SimpleCourseDto.builder()
                        .id(planCourse.getId())
                        .name(planCourse.getName())
                        .date(planCourse.getDate())
                        .orders(planCourse.getOrders())
                        .places(places)
                        .build());
            }

            if (i!=len-1 && !(plansByMonth.get(i).getPlanId().equals(plansByMonth.get(i+1).getPlanId()))) {

                result = new PlanDto();
                result.setPlanId(planCourse.getPlanId());
                result.setCoupleId(planCourse.getCoupleId());
                result.setPlanName(planCourse.getPlanName());
                result.setStartDate(planCourse.getStartDate());
                result.setEndDate(planCourse.getEndDate());
                result.setCreateTime(planCourse.getCreateTime());
                result.setUpdateTime(planCourse.getUpdateTime());
                System.out.println(result);

                result.setCourses(courses);
                resultList.add(result);
                courses = new ArrayList<> ();
                lastPlanId = planCourse.getPlanId();
            }
            else if (i==len-1) {
                if (plansByMonth.get(i).getPlanId().equals(lastPlanId)) {
                    result.setCourses(courses);
                    resultList.add(result);
                }
                else {
                    result = new PlanDto();
                    result.setPlanId(planCourse.getPlanId());
                    result.setCoupleId(planCourse.getCoupleId());
                    result.setPlanName(planCourse.getPlanName());
                    result.setStartDate(planCourse.getStartDate());
                    result.setEndDate(planCourse.getEndDate());
                    result.setCreateTime(planCourse.getCreateTime());
                    result.setUpdateTime(planCourse.getUpdateTime());
                    System.out.println(result);

                    result.setCourses(courses);
                    resultList.add(result);
                }
            }
        }

        if (result.getPlanId() == null) {
            return null;
        }
        return resultList;
    }

    /**
     * 커플 일정 일별 조회 day
     */
    public PlanDto getPlanListByDay(Long coupleId, String date) {
        System.out.println("일정 일별 조회");
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6,8);

        String YearMonthDay = year+"-"+month+"-"+day;

        List<PlanCourseDto> plansByDay = planRepository.findByCoupleIdAndDayQueryDsl(coupleId, YearMonthDay);
        List<SimpleCourseDto> courses = new ArrayList<> ();
        PlanDto result = new PlanDto();
        Long lastPlanId = 0L;
        for (PlanCourseDto planCourse : plansByDay) {
            System.out.println(planCourse);

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
                List<SimplePlaceDto> places = placeRepository.findSimplePlaceDtoByCourseIdQueryDsl(planCourse.getId());

                courses.add(SimpleCourseDto.builder()
                        .id(planCourse.getId())
                        .name(planCourse.getName())
                        .date(planCourse.getDate())
                        .orders(planCourse.getOrders())
                        .places(places)
                        .build());
            }
        }
        System.out.println(courses);
        result.setCourses(courses);
        if (result.getPlanId() == null) {
            return null;
        }
        return result;
    }
}
