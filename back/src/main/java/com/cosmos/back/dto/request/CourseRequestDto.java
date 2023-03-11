package com.cosmos.back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequestDto {
    private String name;
    private String startDate;
    private String endDate;
    private String subCategory;
    private Long planId;
    private Long placeId;
    private Long userSeq;
}
