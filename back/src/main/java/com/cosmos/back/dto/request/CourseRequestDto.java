package com.cosmos.back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequestDto {
    private String name;
    private String date;
    private String subCategory;
    private List<String> categories;
    private Long placeId;
    private Long userSeq;
}
