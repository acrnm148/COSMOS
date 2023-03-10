package com.cosmos.back.repository.course;

import com.cosmos.back.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {
    Optional<Course> findById(Long courseId);
}
