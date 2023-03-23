package com.cosmos.back.dto.response.review;

import com.cosmos.back.model.ReviewCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private List<ReviewCategory> categories;
    private Integer score;
    private String contents;
    private Long userId;
    private String nickname;
    private String createdTime;
    private String img1;
    private String img2;
    private String img3;
}
