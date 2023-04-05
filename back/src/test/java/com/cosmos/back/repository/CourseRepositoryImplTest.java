package com.cosmos.back.repository;

import com.cosmos.back.config.TestConfig;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.CoursePlace;
import com.cosmos.back.model.User;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TestConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoursePlaceRepository coursePlaceRepository;

    @Test
    @DisplayName("CourseRepositoryImpl 코스 ID로 코스로부터 CourseResponseDto 가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void findByCourseIdQueryDSL() {
        User user = User.builder().userName("mock user").courses(new ArrayList<>()).build();
        userRepository.save(user);

        Place place1 = Place.builder().coursePlaces(new ArrayList<>()).name("mock place1").build();
        Place place2 = Place.builder().coursePlaces(new ArrayList<>()).name("mock place2").build();

        placeRepository.save(place1);
        placeRepository.save(place2);

        Course course = Course.createCourse(user);
        courseRepository.save(course);

        CoursePlace coursePlace1 = CoursePlace.createCoursePlace(course, place1, 1);
        CoursePlace coursePlace2 = CoursePlace.createCoursePlace(course, place2, 2);
        coursePlaceRepository.save(coursePlace1);
        coursePlaceRepository.save(coursePlace2);

        CourseResponseDto courseResponseDto = courseRepository.findByCourseIdQueryDSL(course.getId());

        assertThat(courseResponseDto.getCourseId()).isEqualTo(course.getId());
    }

    @Test
    @DisplayName("CourseRepositoryImpl 유저 Seq로 코스로부터 CourseResponseDto 가져오기(wish가 true여야함)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void findAllCourseByUserSeq() {
        User user = User.builder().userName("mock user").courses(new ArrayList<>()).build();
        userRepository.save(user);

        Place place1 = Place.builder().coursePlaces(new ArrayList<>()).name("mock place1").build();
        Place place2 = Place.builder().coursePlaces(new ArrayList<>()).name("mock place2").build();

        placeRepository.save(place1);
        placeRepository.save(place2);

        Course course = Course.createCourse(user);
        course.setWish(true);
        courseRepository.save(course);

        CoursePlace coursePlace1 = CoursePlace.createCoursePlace(course, place1, 1);
        CoursePlace coursePlace2 = CoursePlace.createCoursePlace(course, place2, 2);
        coursePlaceRepository.save(coursePlace1);
        coursePlaceRepository.save(coursePlace2);

        List<CourseResponseDto> courseResponseDto = courseRepository.findAllCourseByUserSeq(user.getUserSeq());

        assertThat(courseResponseDto.size()).isEqualTo(1);
        assertThat(courseResponseDto.get(0).getCourseId()).isEqualTo(course.getId());
    }
}