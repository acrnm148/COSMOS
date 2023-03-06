package com.cosmos.back.service;

import com.cosmos.back.dto.response.place.FestivalResponseDto;
import com.cosmos.back.dto.response.place.TourResponseDto;
import com.cosmos.back.repository.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceRepository placeRepository;

    // QueryDsl로 관광 상세 정보 받아오기
    public TourResponseDto detailTour (Long placeId) {
        return placeRepository.findTourByPlaceIdQueryDsl(placeId);
    }

    // QueryDsl로 축제 상세 정보 받아오기
    public FestivalResponseDto detailFestival (Long placeId) {
        return placeRepository.findFestivalByPlaceIdQueryDsl(placeId);
    }
}
