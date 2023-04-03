package com.cosmos.back.repository.course;

import com.cosmos.back.model.CoursePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePlaceRepository extends JpaRepository<CoursePlace, Long> {
    public List<CoursePlace> findAllByCourseId(Long courseId);

    @Query(value = "SELECT * FROM courseplace cp WHERE cp.course_id = :courseId AND cp.place_id = :placeId", nativeQuery = true)
    public CoursePlace findByCourseIdAndPlaceId(@Param("courseId") Long courseId, @Param("placeId") Long placeId);
}
