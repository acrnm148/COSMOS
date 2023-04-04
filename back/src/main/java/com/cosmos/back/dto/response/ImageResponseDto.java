package com.cosmos.back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ImageResponseDto {
    private Long imageId;
    private String imageUrl;
    private Long reviewId;
    private String createdTime;
    private Long placeId;
}
