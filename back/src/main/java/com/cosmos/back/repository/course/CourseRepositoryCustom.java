package com.cosmos.back.repository.course;

import com.cosmos.back.dto.CourseDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.User;

import java.util.List;

public interface CourseRepositoryCustom {


    public List<CourseDto> findAllByUserSeqQueryDSL(Long userSeq);
    public CourseDto findAllByUserSeqAndCourseIdQueryDSL(Long userSeq, Long courseId);
}
