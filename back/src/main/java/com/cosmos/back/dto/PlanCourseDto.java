package com.cosmos.back.dto;

import com.cosmos.back.model.CoursePlace;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanCourseDto {
    private Long planId;
    private Long coupleId;
    private String planName;
    private String startDate;
    private String endDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long id;
    private String name;
    private String date;
    //private String subCategory;
    private Integer orders;
}
