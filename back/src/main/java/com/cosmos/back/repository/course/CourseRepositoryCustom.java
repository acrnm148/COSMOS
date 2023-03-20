package com.cosmos.back.repository.course;

import com.cosmos.back.dto.MyCoursePlaceDto;
import com.cosmos.back.model.CoursePlace;

import java.util.List;

public interface CourseRepositoryCustom {

    public List<MyCoursePlaceDto> findAllByUserSeqQueryDSL(Long userSeq);

    public List<MyCoursePlaceDto> findAllByUserSeqAndCourseIdQueryDSL(Long userSeq, Long courseId);
    public Long deleteCoursePlaceQueryDSL(Long courseId, CoursePlace cp);
}
