package com.cosmos.back.repository.course;

import com.cosmos.back.dto.MyCoursePlaceDto;
import com.cosmos.back.dto.request.CourseUpdateAddDelRequestDto;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.CoursePlace;

import java.util.List;

public interface CourseRepositoryCustom {

    public List<MyCoursePlaceDto> findAllByUserSeqAndCourseIdQueryDSL(Long userSeq, Long courseId);

    public Long deleteCoursePlaceQueryDSL(Long courseId, CoursePlace cp);

    // 내 찜한 코스 보기
    public List<CourseResponseDto> findAllCourseByUserSeq(Long userSeq);
}
