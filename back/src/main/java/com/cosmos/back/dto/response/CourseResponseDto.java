package com.cosmos.back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponseDto {
    private Long courseId;
    private Long planId;
    private String name;
    private String startDate;
    private String endDate;
    private String subCategory;
    private String placeId;
    private Integer order; //순서
}
