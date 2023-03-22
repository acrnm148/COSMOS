package com.cosmos.back.dto.response;

import com.cosmos.back.dto.MyCoursePlaceDto;
import com.cosmos.back.dto.SimplePlaceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseResponseDto {
    private Long courseId;
    private String name;
    private String date;
    private String subCategory;
    private Integer orders;
    private List<SimplePlaceDto> dto;

}

