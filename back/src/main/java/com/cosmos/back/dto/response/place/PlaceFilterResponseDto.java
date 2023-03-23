package com.cosmos.back.dto.response.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceFilterResponseDto {
    private String filter;
    private List<PlaceSearchListResponseDto> places;
}
