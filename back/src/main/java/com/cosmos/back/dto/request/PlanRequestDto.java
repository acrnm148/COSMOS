package com.cosmos.back.dto.request;

import com.cosmos.back.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDto {
    private Long coupleId;
    private String mainCategory;
    private String startDate;
    private String endDate;
    private List<Course> courses;
}
