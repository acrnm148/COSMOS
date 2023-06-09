package com.cosmos.back.dto.response.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoCompleteResponseDto {
    private Long placeId;
    private String name;
    private String address;
    private String thumbNailUrl;
    private String type;
}
