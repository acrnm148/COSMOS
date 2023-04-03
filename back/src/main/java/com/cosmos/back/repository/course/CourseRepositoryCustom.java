package com.cosmos.back.repository.course;

import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.model.CoursePlace;

import java.util.List;

public interface CourseRepositoryCustom {

    public CourseResponseDto findByCourseIdQueryDSL(Long courseId);

    // 내 찜한 코스 보기
    public List<CourseResponseDto> findAllCourseByUserSeq(Long userSeq);
}
