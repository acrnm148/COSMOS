package com.cosmos.back.dto.response;

import com.cosmos.back.dto.MyCoursePlaceDto;
import com.cosmos.back.dto.SimplePlaceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseResponseDto {
    private String name;
    private String date;
    private Long courseId;
    private List<SimplePlaceDto> places = new ArrayList<>();
    private Integer orders;
    private Double midLatitude;
    private Double midLongitude;

    public CourseResponseDto (String name, String date, Long courseId, Integer orders) {
        this.name = name;
        this.date = date;
        this.courseId = courseId;
        this.orders = orders;
    }

    public CourseResponseDto (String name, String date, Long courseId) {
        this.name = name;
        this.date = date;
        this.courseId = courseId;
    }
}

