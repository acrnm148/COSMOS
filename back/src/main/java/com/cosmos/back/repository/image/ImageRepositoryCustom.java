package com.cosmos.back.repository.image;

import com.cosmos.back.dto.response.ImageResponseDto;

import java.util.List;

public interface ImageRepositoryCustom {

    // QueryDsl로 월별 이미지 가져오기
    public List<ImageResponseDto> findMonthImage(Long coupleId, Long month);

    // QueryDsl로 일별 이미지 가져오기
    public List<ImageResponseDto> findDayImage(Long coupleId, Long day);
}
