package com.cosmos.back.repository.place;

import com.cosmos.back.dto.response.place.PlaceListResponseDto;

import java.util.List;

public interface UserPlaceRepositoryCustom {

    // QueryDsl로 UserPlace 지우기(찜 삭제)
    public Long deleteUserPlaceQueryDsl (Long placeId, Long userSeq);

    // QueryDsl로 UserPlace 가져오기(찜 목록 조회)
    public List<PlaceListResponseDto> findLikePlaces (Long userSeq, Integer limit, Integer offset);
}
