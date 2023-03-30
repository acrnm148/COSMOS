package com.cosmos.back.repository.image;

import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.Image;

import java.util.List;

public interface ImageRepositoryCustom {

    // QueryDsl로 월별 이미지 가져오기
    public List<ImageResponseDto> findMonthImage(Long coupleId, Long month);

    // QueryDsl로 일별 이미지 가져오기
    public List<ImageResponseDto> findDayImage(Long coupleId, Long day);

    // QueryDsl로 전체 이미지 가져오기
    public List<Image> findAllByCoupleId(Long coupleId, Integer limit, Integer offset);
}
