package com.cosmos.back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "시도", example = "서울특별시")
    private String sido;

    @Schema(description = "구군", example = "강남구")
    private String gugun;

    @Schema(description = "장소 카테고리", allowableValues = {"cafe", "restaurant", "leisure", "shopping", "culture", "tour", "accomodataion"})
    private List<String> categories;

    @Schema(description = "유저 Seq", example = "1")
    private Long userSeq;
}
