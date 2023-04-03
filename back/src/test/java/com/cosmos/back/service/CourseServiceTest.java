package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.CoursePlace;
import com.cosmos.back.model.User;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoursePlaceRepository coursePlaceRepository;

    @SpyBean
    private PlaceRepository placeRepository;

    @SpyBean
    private UserRepository userRepository;
//
//    @Test
//    @DisplayName("CourseService saveCourse 메소드")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void saveCourse() throws Exception {
//        User user = User.builder().userName("테스트 유저가 될지도!!!!").build();
//        userRepository.save(user);
//
//        User mockUser = User.builder().userSeq(1L).courses(new ArrayList<>()).build();
//
//        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockUser));
//
//        Course course = courseService.saveCourse(1L);
//
//        Course foundCourse = courseRepository.findById(course.getId()).orElseThrow(() -> new IllegalArgumentException("no such data"));
//
//        assertEquals(course.getId(), foundCourse.getId());
//    }
//
//    @Test
//    @DisplayName("CourseService selectPlaces 메소드")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void selectPlaces() throws Exception {
//        Place mockPlace1 = Place.builder().address("서울특별시 강남구").type("cafe").thumbNailUrl("나는 썸네일 url 이다").build();
//        Place mockPlace2 = Place.builder().address("서울특별시 강남구").type("restaurant").parkingYn("주차 가능한가?").build();
//        Place mockPlace3 = Place.builder().address("서울특별시 서초구").type("accommodation").latitude("이건 경도인가 위도인가?").build();
//        Place mockPlace4 = Place.builder().address("서울특별시 종로구").type("shopping").img5("나는 img 4인가 img 5인가?").build();
//
//        placeRepository.save(mockPlace1);
//        placeRepository.save(mockPlace2);
//        placeRepository.save(mockPlace3);
//        placeRepository.save(mockPlace4);
//
//        List<String> testCategories = new ArrayList<>();
//        testCategories.add("cafe");
//        testCategories.add("restaurant");
//
//        List<Place> places = courseService.selectPlaces(testCategories, "서울특별시", "강남구");
//
//        // 2개를 가져와야 한다.
//        assertEquals(places.size(), 2);
//
//        // testCategories의 type 순서대로 가져와야 한다.
//        assertEquals(places.get(0).getType(), testCategories.get(0));
//        assertEquals(places.get(1).getType(), testCategories.get(1));
//    }
//
//    @Test
//    @DisplayName("CourseService saveSimplePlaceDtoList 메소드")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void saveSimplePlaceDtoList() throws Exception {
//        when(placeRepository.findScoreByPlaceIdQueryDsl(anyLong())).thenReturn(3.14);
//
//        User mockUser = User.builder().userName("mock User").build();
//        userRepository.save(mockUser);
//
//        Place mockPlace1 = Place.builder().address("서울특별시 강남구").type("cafe").thumbNailUrl("나는 썸네일 url 이다").coursePlaces(new ArrayList<>()).build();
//        Place mockPlace2 = Place.builder().address("서울특별시 강남구").type("restaurant").parkingYn("주차 가능한가?").coursePlaces(new ArrayList<>()).build();
//
//        placeRepository.save(mockPlace1);
//        placeRepository.save(mockPlace2);
//
//        Course mockCourse = Course.builder().user(mockUser).coursePlaces(new ArrayList<>()).build();
//        courseRepository.save(mockCourse);
//
//        CourseResponseDto courseResponseDto = CourseResponseDto.builder().courseId(mockCourse.getId()).places(new ArrayList<>()).build();
//
//        List<Place> places = new ArrayList<>();
//
//        places.add(mockPlace1);
//        places.add(mockPlace2);
//
//        List<Long> coursePlaceIds = courseService.saveSimplePlaceDtoList(mockCourse, courseResponseDto, places);
//
//        // courseResponseDto의 places에 2개가 저장되어야 한다.
//        assertEquals(courseResponseDto.getPlaces().size(), 2);
//
//        // orders가 순서대로 저장되어야한다.
//        assertEquals(courseResponseDto.getPlaces().get(0).getOrders(), 1);
//        assertEquals(courseResponseDto.getPlaces().get(1).getOrders(), 2);
//
//        // courseplaceRepository에 저장되어야 한다.
//        for (Long id : coursePlaceIds) {
//            CoursePlace coursePlace = coursePlaceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
//
//            assertEquals(coursePlace.getId(), id);
//        }
//
//        // 평점이 3.14이어야 한다.
//        assertEquals(courseResponseDto.getPlaces().get(0).getScore(), 3.14);
//    }
//
//    @Test
//    @DisplayName("CourseService 코스 생성")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 코스_생성() throws Exception {
//        User mockUser = User.builder().userName("mock user").build();
//        userRepository.save(mockUser);
//
//        Place place1 = Place.builder().name("mock place 1").type("cafe").build();
//        Place place2 = Place.builder().name("mock place 2").type("restaurant").build();
//
//        placeRepository.save(place1);
//        placeRepository.save(place2);
//
//        List<String> mockCategories = new ArrayList<>();
//        mockCategories.add("cafe");
//        mockCategories.add("restaurant");
//
//        CourseRequestDto mockCourseRequestDto = CourseRequestDto.builder().sido("서울특별시").gugun("강남구").categories(mockCategories).userSeq(1L).build();
//
//        CourseResponseDto courseResponseDto = courseService.createCourse(mockCourseRequestDto);
//
//        assertEquals(courseResponseDto.getPlaces().size(), 2);
//    }
//
//    @Test
//    @DisplayName("CourseService 코스 찜")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 코스_찜() throws Exception {
//        Course mockCourse = Course.builder().name("mock course").build();
//        courseRepository.save(mockCourse);
//
//        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(mockCourse));
//
//        Map<String, String> mockMap = courseService.likeCourse(1L);
//
//        assertEquals(mockMap.get("wish"), "true");
//    }
}
