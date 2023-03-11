package com.cosmos.back.repository.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePlaceRepository extends JpaRepository<com.cosmos.back.model.CoursePlace, Long> {
}
