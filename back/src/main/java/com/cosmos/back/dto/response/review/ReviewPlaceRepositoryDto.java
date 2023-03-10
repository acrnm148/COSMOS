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
    Long reviewId;
//    List<String> categories;
    Integer score;
    String contents;
    Long userId;
}
