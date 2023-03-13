package com.cosmos.back.service;

import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.model.*;
import com.cosmos.back.model.place.*;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.plan.PlanRepository;
import com.cosmos.back.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class CourseService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final CourseRepository courseRepository;
    private final CoursePlaceRepository coursePlaceRepository;

    // 코스 생성
    @Transactional
    public Long createCourse(CourseRequestDto dto) {
        Long userSeq = dto.getUserSeq();
        Long placeId = dto.getPlaceId();

        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        // 데이트코스 저장
        Course course = Course.createCourse(user, dto.getName(), dto.getDate(), dto.getSubCategory());
        courseRepository.save(course);

        List<Place> places = new ArrayList<>();

        for (String category : dto.getCategories()) {
            places.add(chooseOne(category));
        }

        // 순서
        int orders = 1;
        for (Place p : places) {
            CoursePlace coursePlace = CoursePlace.createCoursePlace(course, p, orders++);
            coursePlaceRepository.save(coursePlace);
        }

        return course.getId();
    }

    public Place chooseOne(String type) {
        List<Place> places = placeRepository.findAllByType(type);

        // 빅데이터 알고리즘
        return places.get(0);
    }

    // 코스 삭제
    @Transactional
    public Long deleteCourse (Long courseId) {
        List<CoursePlace> coursePlaces = coursePlaceRepository.findAllByCourseId(courseId);

        for (CoursePlace coursePlace : coursePlaces) {
            coursePlaceRepository.deleteById(coursePlace.getId());
        }

        courseRepository.deleteById(courseId);

        return courseId;
    }

    // 코스 수정
    @Transactional
    public Long updateCourse (Long courseId, CourseRequestDto dto) {
        String courseName =
    }
}
