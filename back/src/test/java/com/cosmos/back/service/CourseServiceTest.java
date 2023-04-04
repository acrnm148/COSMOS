package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.SimplePlaceDto;
import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.dto.request.CourseUpdateRequstDto;
import com.cosmos.back.dto.request.CouserUesrRequestDto;
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
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @SpyBean
    private CourseRepository courseRepository;

    @SpyBean
    private CoursePlaceRepository coursePlaceRepository;

    @SpyBean
    private PlaceRepository placeRepository;

    @SpyBean
    private UserRepository userRepository;

    @Test
    @DisplayName("CourseService saveCourse 메소드")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void saveCourse() throws Exception {
        User user = User.builder().userName("테스트 유저가 될지도!!!!").build();
        userRepository.save(user);

        User mockUser = User.builder().userSeq(user.getUserSeq()).courses(new ArrayList<>()).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockUser));

        Course course = courseService.saveCourse(user.getUserSeq());

        Course foundCourse = courseRepository.findById(course.getId()).orElseThrow(() -> new IllegalArgumentException("no such data"));

        assertEquals(course.getId(), foundCourse.getId());
    }

//    @Test
//    @DisplayName("CourseService chooseOne 메소드 가져온 place List 메소드 크기가 0일 때")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void chooseOne_place_list_size_0() throws Exception {
//        User mockUser = User.builder().type1("EAT").type2("EAY").build();
//        userRepository.save(mockUser);
//
//        Place mockPlace = Place.builder().address("서울특별시 강서구").tendency("dfi").type("cafe").build();
//        placeRepository.save(mockPlace);
//
//        Place returnPlace = courseService.chooseOne("cafe", "서울특별시", "강서구", mockUser.getUserSeq());
//
//        assertEquals(returnPlace.getId(), mockPlace.getId());
//    }

    @Test
    @DisplayName("CourseService selectPlaces 메소드 정상적으로 가져올 때")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void selectPlaces() throws Exception {
        User mockUser1 = User.builder().type1("EAY").type2("EAT").build();
        User mockUser2 = User.builder().type1("JOT").type2("JOY").build();
        userRepository.save(mockUser1);
        userRepository.save(mockUser2);

        Place mockPlace1 = Place.builder().address("서울특별시 강남구").type("cafe").tendency("spo").thumbNailUrl("나는 썸네일 url 이다").build();
        Place mockPlace2 = Place.builder().address("서울특별시 강남구").type("restaurant").tendency("spoi").parkingYn("주차 가능한가?").build();
        Place mockPlace3 = Place.builder().address("서울특별시 서초구").type("accommodation").tendency("spoi").latitude("이건 경도인가 위도인가?").build();
        Place mockPlace4 = Place.builder().address("서울특별시 종로구").type("shopping").tendency("spo").img5("나는 img 4인가 img 5인가?").build();
        Place mockPlace5 = Place.builder().address("서울특별시 강남구").type("cafe").tendency("dfi").thumbNailUrl("나는 썸네일 url 이다").build();
        Place mockPlace6 = Place.builder().address("서울특별시 강남구").type("restaurant").tendency("dfi").thumbNailUrl("55555").build();

        placeRepository.save(mockPlace1);
        placeRepository.save(mockPlace2);
        placeRepository.save(mockPlace3);
        placeRepository.save(mockPlace4);
        placeRepository.save(mockPlace5);
        placeRepository.save(mockPlace6);

        List<String> testCategories = new ArrayList<>();
        testCategories.add("cafe");
        testCategories.add("restaurant");

        List<Place> places1 = courseService.selectPlaces(testCategories, "서울특별시", "강남구", mockUser1.getUserSeq());
        List<Place> places2 = courseService.selectPlaces(testCategories, "서울특별시", "강남구", mockUser2.getUserSeq());

        // 2개를 가져와야 한다.
        assertEquals(places1.size(), 2);

        // testCategories의 type 순서대로 가져와야 한다.
        assertEquals(places1.get(0).getType(), testCategories.get(0));
        assertEquals(places1.get(1).getType(), testCategories.get(1));

        assertEquals(places2.size(), 2);

        assertEquals(places2.get(0).getType(), testCategories.get(0));
        assertEquals(places2.get(1).getType(), testCategories.get(1));
    }

//    @Test
//    @DisplayName("CourseService selectPlaces 메소드(2) 유저 타입에 맞는 장소가 없을 때")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void selectPlaces2() throws Exception {
//        User mockUser = User.builder().type1("EAT").type2("EAY").build();
//        userRepository.save(mockUser);
//
//        Place mockPlace1 = Place.builder().address("서울특별시 강남구").type("cafe").tendency("spo").thumbNailUrl("나는 썸네일 url 이다").build();
//        Place mockPlace2 = Place.builder().address("서울특별시 강남구").type("restaurant").tendency("spo").parkingYn("주차 가능한가?").build();
//        Place mockPlace3 = Place.builder().address("서울특별시 서초구").type("accommodation").tendency("spo").latitude("이건 경도인가 위도인가?").build();
//        Place mockPlace4 = Place.builder().address("서울특별시 종로구").type("shopping").tendency("spo").img5("나는 img 4인가 img 5인가?").build();
//        Place mockPlace5 = Place.builder().address("서울특별시 강남구").type("cafe").tendency("dfi").thumbNailUrl("55555").build();
//
//        placeRepository.save(mockPlace1);
//        placeRepository.save(mockPlace2);
//        placeRepository.save(mockPlace3);
//        placeRepository.save(mockPlace4);
//        placeRepository.save(mockPlace5);
//
//        List<String> testCategories = new ArrayList<>();
//        testCategories.add("cafe");
//        testCategories.add("restaurant");
//        testCategories.add("cafe");
//
//        List<Place> places = courseService.selectPlaces(testCategories, "서울특별시", "강남구", mockUser.getUserSeq());
//
//        // 3개를 가져와야 한다.
//        assertEquals(places.size(), 3);
//
//        // testCategories의 type 순서대로 가져와야 한다.
//        assertEquals(places.get(0).getType(), testCategories.get(0));
//        assertEquals(places.get(1).getType(), testCategories.get(1));
//        assertEquals(places.get(2).getType(), testCategories.get(2));
//
//        assertEquals(places.get(0).getThumbNailUrl(), mockPlace1.getThumbNailUrl());
//
//        // 조건을 모두 만족하는 카페가 없으므로 마지막에는 5번째 mockPlace5를 가져와야한다.
//        assertEquals(places.get(2).getThumbNailUrl(), "55555");
//    }

    @Test
    @DisplayName("CourseService saveSimplePlaceDtoList 메소드")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void saveSimplePlaceDtoList() throws Exception {
        when(placeRepository.findScoreByPlaceIdQueryDsl(anyLong())).thenReturn(3.14);

        User mockUser = User.builder().userName("mock User").build();
        userRepository.save(mockUser);

        Place mockPlace1 = Place.builder().address("서울특별시 강남구").type("cafe").thumbNailUrl("나는 썸네일 url 이다").coursePlaces(new ArrayList<>()).build();
        Place mockPlace2 = Place.builder().address("서울특별시 강남구").type("restaurant").parkingYn("주차 가능한가?").coursePlaces(new ArrayList<>()).build();

        placeRepository.save(mockPlace1);
        placeRepository.save(mockPlace2);

        Course mockCourse = Course.builder().user(mockUser).coursePlaces(new ArrayList<>()).build();
        courseRepository.save(mockCourse);

        CourseResponseDto courseResponseDto = CourseResponseDto.builder().courseId(mockCourse.getId()).places(new ArrayList<>()).build();

        List<Place> places = new ArrayList<>();

        places.add(mockPlace1);
        places.add(mockPlace2);

        List<Long> coursePlaceIds = courseService.saveSimplePlaceDtoList(mockCourse, courseResponseDto, places);

        // courseResponseDto의 places에 2개가 저장되어야 한다.
        assertEquals(courseResponseDto.getPlaces().size(), 2);

        // orders가 순서대로 저장되어야한다.
        assertEquals(courseResponseDto.getPlaces().get(0).getOrders(), 1);
        assertEquals(courseResponseDto.getPlaces().get(1).getOrders(), 2);

        // courseplaceRepository에 저장되어야 한다.
        for (Long id : coursePlaceIds) {
            CoursePlace coursePlace = coursePlaceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));

            assertEquals(coursePlace.getId(), id);
        }

        // 평점이 3.14이어야 한다.
        assertEquals(courseResponseDto.getPlaces().get(0).getScore(), 3.14);
    }

    @Test
    @DisplayName("CourseService 코스 생성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 코스_생성() throws Exception {
        User mockUser = User.builder().userName("mock user").type1("EAT").type2("EAY").courses(new ArrayList<>()).build();
        userRepository.save(mockUser);

        Place place1 = Place.builder().address("서울특별시 강남구").name("mock place 1").type("cafe").tendency("spo").coursePlaces(new ArrayList<>()).build();
        Place place2 = Place.builder().address("서울특별시 강남구").name("mock place 2").type("restaurant").tendency("spo").coursePlaces(new ArrayList<>()).build();

        placeRepository.save(place1);
        placeRepository.save(place2);

        List<String> mockCategories = new ArrayList<>();
        mockCategories.add("cafe");
        mockCategories.add("restaurant");

        CourseRequestDto mockCourseRequestDto = CourseRequestDto.builder().sido("서울특별시").gugun("강남구").categories(mockCategories).userSeq(mockUser.getUserSeq()).build();

        CourseResponseDto courseResponseDto = courseService.createCourse(mockCourseRequestDto);

        assertEquals(courseResponseDto.getPlaces().size(), 2);
    }

    @Test
    @DisplayName("CourseService 코스 찜")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 코스_찜() throws Exception {
        Course mockCourse = Course.builder().id(1995L).wish(false).name("before test").build();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockCourse));

        Map<String, String> ret = courseService.likeCourse(1995L, "mock course name");

        assertEquals(ret.get("wish"), "true");
        assertEquals(ret.get("name"), "mock course name");
    }

    @Test
    @DisplayName("CourseService 코스 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 코스_삭제() throws Exception {
        List<CoursePlace> mockCoursePlaceList = new ArrayList<>();
        mockCoursePlaceList.add(CoursePlace.builder().id(1L).build());

        when(coursePlaceRepository.findAllByCourseId(anyLong())).thenReturn(mockCoursePlaceList);

        doNothing().when(coursePlaceRepository).deleteById(anyLong());

        doNothing().when(courseRepository).deleteById(anyLong());

        Long id = courseService.deleteCourse(1995L);

        assertEquals(id, 1995L);
    }

    @Test
    @DisplayName("CourseService 코스 수정")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 코스_수정() throws Exception {
        reset(coursePlaceRepository);

        Place place1 = Place.builder().type("cafe").name("cafe 1").tendency("spo").coursePlaces(new ArrayList<>()).build();
        Place place2 = Place.builder().type("restaurant").name("restaurant 1").tendency("spo").coursePlaces(new ArrayList<>()).build();
        Place place3 = Place.builder().type("cafe").name("cafe 2").tendency("spo").coursePlaces(new ArrayList<>()).build();

        placeRepository.save(place1);
        placeRepository.save(place2);
        placeRepository.save(place3);

        List<Long> placeIds = new ArrayList<>();

        placeIds.add(place1.getId());
        placeIds.add(place2.getId());
        placeIds.add(place3.getId());

        CouserUesrRequestDto couserUesrRequestDto = CouserUesrRequestDto.builder().name("course name before change").placeIds(placeIds).build();

        User user = User.builder().userName("user 1").courses(new ArrayList<>()).build();

        userRepository.save(user);

        Long courseId = courseService.createCourseByUser(user.getUserSeq(), couserUesrRequestDto);

        placeIds.clear();

        Place place4 = Place.builder().type("cafe").name("cafe 3").tendency("spo").coursePlaces(new ArrayList<>()).build();

        placeRepository.save(place4);

        placeIds.add(place4.getId());
        placeIds.add(place3.getId());
        placeIds.add(place2.getId());

        CourseUpdateRequstDto courseUpdateRequstDto = CourseUpdateRequstDto.builder().name("course name after change").placeIds(placeIds).build();

        courseService.updateCourse(courseId, courseUpdateRequstDto);

        List<CoursePlace> coursePlaces = coursePlaceRepository.findAllByCourseId(courseId);

        assertEquals(coursePlaces.size(), 3);

        assertEquals(coursePlaces.get(0).getOrders(), 1);
        assertEquals(coursePlaces.get(1).getOrders(), 2);
        assertEquals(coursePlaces.get(2).getOrders(), 3);

        assertEquals(coursePlaces.get(0).getPlace().getId(), place4.getId());
        assertEquals(coursePlaces.get(1).getPlace().getId(), place3.getId());
        assertEquals(coursePlaces.get(2).getPlace().getId(), place2.getId());
    }

    @Test
    @DisplayName("CourseService 내 찜한 코스 목록 보기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 찜한_코스_목록_보기() throws Exception {
        CourseResponseDto courseResponseDto = CourseResponseDto.builder().courseId(1L).build();
        List<CourseResponseDto> courseResponseDtos = new ArrayList<>();
        courseResponseDtos.add(courseResponseDto);

        CoursePlace coursePlace = CoursePlace.builder().place(Place.builder().id(1L).build()).course(Course.builder().id(1L).build()).build();
        List<CoursePlace> coursePlaces = new ArrayList<>();
        coursePlaces.add(coursePlace);

        SimplePlaceDto simplePlaceDto = SimplePlaceDto.builder().build();

        when(courseRepository.findAllCourseByUserSeq(anyLong())).thenReturn(courseResponseDtos);
        when(coursePlaceRepository.findAllByCourseId(anyLong())).thenReturn(coursePlaces);
        when(placeRepository.findSimplePlaceDtoByPlaceIdQueryDsl(anyLong(), anyLong())).thenReturn(simplePlaceDto);
        when(placeRepository.findScoreByPlaceIdQueryDsl(anyLong())).thenReturn(3.14);

        List<CourseResponseDto> list = courseService.listLikeCourse(1L);

        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getPlaces().get(0).getScore(), 3.14);
    }

    @Test
    @DisplayName("CourseService 위도 경도 중앙값 계산하기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 위도_경도_중앙값_계산() throws Exception {
        CourseResponseDto courseResponseDto = CourseResponseDto.builder().build();

        Place place1 = Place.builder().latitude("3.0").longitude("3.0").build();
        Place place2 = Place.builder().latitude("4.0").longitude("4.0").build();
        Place place3 = Place.builder().latitude("5.0").longitude("5.0").build();

        List<Place> places = new ArrayList<>();

        places.add(place1);
        places.add(place2);
        places.add(place3);

        courseService.saveMidLatitudeAndMidLongitude(courseResponseDto, places);

        assertEquals(courseResponseDto.getMidLatitude(), 4.0);
        assertEquals(courseResponseDto.getMidLongitude(), 4.0);
    }

    @Test
    @DisplayName("CourseService 내 코스 상세보기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 내_코스_상세보기() throws Exception {
        reset(courseRepository, coursePlaceRepository, placeRepository);

        CourseResponseDto courseResponseDto = CourseResponseDto.builder().courseId(1L).build();

        SimplePlaceDto simplePlaceDto = SimplePlaceDto.builder().courseId(1L).placeId(1L).build();

        List<CoursePlace> coursePlaces = new ArrayList<>();
        Place place = Place.builder().id(1L).build();
        Course course = Course.builder().id(1L).build();
        CoursePlace coursePlace = CoursePlace.builder().id(1L).course(course).place(place).build();
        coursePlaces.add(coursePlace);


        when(courseRepository.findByCourseIdQueryDSL(anyLong())).thenReturn(courseResponseDto);
        when(coursePlaceRepository.findAllByCourseId(anyLong())).thenReturn(coursePlaces);
        when(placeRepository.findSimplePlaceDtoByPlaceIdQueryDsl(anyLong(), anyLong())).thenReturn(simplePlaceDto);
        when(placeRepository.findScoreByPlaceIdQueryDsl(anyLong())).thenReturn(3.14);

        CourseResponseDto ret = courseService.getMyCourseDetail(1L);

        assertEquals(ret.getPlaces().size(), 1);
        assertEquals(ret.getPlaces().get(0).getScore(), 3.14);
    }
}
