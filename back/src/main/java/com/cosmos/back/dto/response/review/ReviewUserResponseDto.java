package com.cosmos.back.dto.response.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewUserResponseDto {
    private Long reviewId;
//    private List<String> categories;
    private Integer score;
    private String contents;
    private Long palceId;
}
