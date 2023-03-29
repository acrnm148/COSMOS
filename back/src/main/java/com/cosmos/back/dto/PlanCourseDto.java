package com.cosmos.back.dto;

import lombok.*;

import java.time.LocalDateTime;

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
