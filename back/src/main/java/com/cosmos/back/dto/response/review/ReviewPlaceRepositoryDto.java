package com.cosmos.back.dto.response.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewPlaceRepositoryDto {
    private Long reviewId;
//    private List<String> categories;
    private Integer score;
    private String contents;
    private Long userId;
}
