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
public class ReviewRequestDto {
    private Long placeId;
    private List<String> categories;
    private List<String> indiCategories;
    private Integer score;
    private String contents;
    private Boolean contentsOpen;
    private Boolean imageOpen;
}
