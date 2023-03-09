package com.cosmos.back.service;

import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.Plan;
import com.cosmos.back.model.User;
import com.cosmos.back.model.UserCourse;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.course.PlanRepository;
import com.cosmos.back.repository.course.UserCourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class CourseService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final PlanRepository planRepository;
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final CoursePlaceRepository coursePlaceRepository;

    // 코스 생성
    @Transactional
    public void createCourseService(CourseRequestDto dto) {
        Long userSeq = dto.getUserSeq();
        Long placeId = dto.getPlaceId();
        Long planId = null;

        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Course course = Course.createCourse(dto.getName(), dto.getStartDate(), dto.getEndDate(), dto.getSubCategory());
        courseRepository.save(course);

        UserCourse userCourse = UserCourse.createUserCourse(user, course);
        userCourseRepository.save(userCourse);




    }

}
