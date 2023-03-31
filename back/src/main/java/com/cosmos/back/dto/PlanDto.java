package com.cosmos.back.dto;

import com.cosmos.back.model.Course;
import com.cosmos.back.model.Plan;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PlanDto {
    private Long planId;
    private Long coupleId;
    private String planName;
    private String startDate;
    private String endDate;
    private LocalDateTime    createTime;
    private LocalDateTime updateTime;
    private List<Course> courses;
    private List<CourseIdAndDate> CourseIdAndDateList;

    /**
     * entity to dto
     */
    @Builder //생성자에 빌더 적용 -> allargsconstructor 안쓰기 위함
    public PlanDto(Plan plan) {
        this.planId = plan.getId();
        this.coupleId = plan.getCoupleId();
        this.planName = plan.getPlanName();
        this.startDate = plan.getStartDate();
        this.endDate = plan.getEndDate();
        this.createTime = plan.getCreateTime();
        this.updateTime = plan.getUpdateTime();
        this.courses = plan.getCourses();
    }

    /**
     * dto to entity
     */
    public Plan toEntity() {
        return Plan.builder()
                .id(planId)
                .coupleId(coupleId)
                .planName(planName)
                .startDate(startDate)
                .endDate(endDate)
                .createTime(createTime)
                .updateTime(updateTime)
                .courses(courses)
                .build();
    }
}
