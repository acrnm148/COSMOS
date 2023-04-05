package com.cosmos.back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePlaceResponseDto {
    private Long id;
    private Integer orders;
    private Long courseId;
    private Long placeId;

}
