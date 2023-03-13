package com.cosmos.back.dto;

import com.cosmos.back.dto.response.place.PlaceListResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CourseDto {
    private Long courseId;
    private String name;
    private String date;
    private String subCategory;
    private Integer orders;
    private List<PlaceListResponseDto> dto;

}
