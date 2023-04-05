package com.cosmos.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCourseDto {
    private Long id;
    private String name;
    private String date;
    private Integer orders;
    private List<SimplePlaceDto> places;
}
