package com.cosmos.back.dto.response.review;

import com.cosmos.back.model.Image;
import com.cosmos.back.model.IndiReviewCategory;
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
    private List<IndiReviewCategory> indiReviewCategories;
    private Integer score;
    private String contents;
    private Long userId;
    private String nickname;
    private String createdTime;
    private List<Image> images;
    private Boolean contentsOpen;
    private Boolean imageOpen;
}
