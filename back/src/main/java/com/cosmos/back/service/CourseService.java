package com.cosmos.back.service;

import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.model.*;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.course.UserCourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.plan.PlanRepository;
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

        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        // 데이트코스 저장
        Course course = Course.createCourse(dto.getName(), dto.getDate(), dto.getSubCategory());
        courseRepository.save(course);

        // 유저_데이트코스 저장
        UserCourse userCourse = UserCourse.createUserCourse(user, course);
        userCourseRepository.save(userCourse);

        // 데이트 코스 추천 알고리즘

        // 데이트코스-장소 저장(for문으로 하나씩 저장)
//        for() {
//            CoursePlace coursePlace = CoursePlace.createCoursePlace(course, place);
//            courseRepository.save(coursePlace);
//        }

    }

}
