package com.cosmos.back.repository.place;

public interface UserPlaceRepositoryCustom {

    // QueryDsl로 UserPlace 지우기(찜 삭제)
    public Long deleteUserPlaceQueryDsl (Long placeId, Long userSeq);
}
