package com.cosmos.back.repository.course;

import com.cosmos.back.model.CoursePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePlaceRepository extends JpaRepository<com.cosmos.back.model.CoursePlace, Long> {
    public List<CoursePlace> findAllByCourseId(Long courseId);
}
