package com.cosmos.back.service;

import com.cosmos.back.dto.SimplePlaceDto;
import com.cosmos.back.dto.request.CourseUpdateAddDelRequestDto;
import com.cosmos.back.dto.request.CourseUpdateContentsRequestDto;
import com.cosmos.back.dto.request.CourseUpdateOrdersRequestDto;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.dto.MyCoursePlaceDto;
import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.model.*;
import com.cosmos.back.model.place.*;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * 내 모든 코스 보기
     */
    public List<CourseResponseDto> getMyAllCourses(Long userSeq) {
        List<MyCoursePlaceDto> myCoursePlaces = courseRepository.findAllByUserSeqQueryDSL(userSeq);

        List<CourseResponseDto> courses = new ArrayList<> ();
        List<MyCoursePlaceDto> coursePlaces = new ArrayList<> ();
        Long courseId = 0L;

        for (MyCoursePlaceDto dto : myCoursePlaces) {

            if (!(courseId.equals(dto.getCourseId()))) {
                List<SimplePlaceDto> simplePlaces = new ArrayList<> ();

                CourseResponseDto course = CourseResponseDto.builder()
                        .courseId(dto.getCourseId())
                        .name(dto.getName())
                        .date(dto.getName())
                        .subCategory(dto.getSubCategory())
                        .orders(dto.getOrders())
                        .build();
                for (MyCoursePlaceDto dto2 : myCoursePlaces) {
                    if (!(courseId.equals(dto.getCourseId()))) {
                        break;
                    }
                    simplePlaces.add(SimplePlaceDto.builder()
                            .placeId(dto2.getPlaceId())
                            .placeName(dto2.getPlaceName())
                            .phoneNumber(dto2.getPhoneNumber())
                            .latitude(dto2.getLatitude())
                            .longitude(dto2.getLongitude())
                            .thumbNailUrl(dto2.getThumbNailUrl())
                            .address(dto2.getAddress())
                            .build());
                }
                course.setDto( simplePlaces );
                courses.add(course);
            }
        }

//        System.out.println("내 모든 코스:"+myCoursePlaces);
//        return myCoursePlaces;
        System.out.println("내 모든 코스:"+courses);
        return courses;
    }

    /**
     * 내 모든 코스 보기
     */
    public List<MyCoursePlaceDto> getMyCourseDetail(Long userSeq, Long courseId) {
        List<MyCoursePlaceDto> course = courseRepository.findAllByUserSeqAndCourseIdQueryDSL(userSeq, courseId);
        System.out.println("내 코스 상세:"+course);
        return course;
    }

    // 코스 내용 수정
    @Transactional
    public Long updateCourseContents (Long courseId, CourseUpdateContentsRequestDto dto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        course.setName(dto.getName());
        course.setSubCategory(dto.getSubCategory());
        courseRepository.save(course);
        return course.getId();
    }

    // 코스 수정(추가)
    @Transactional
    public void updateCourseAdd (Long courseId, CourseUpdateAddDelRequestDto dto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Place place = placeRepository.findById(dto.getPlaceId()).orElseThrow(() -> new IllegalArgumentException("no such data"));
        int size = course.getCoursePlaces().size();
        CoursePlace coursePlace = CoursePlace.createCoursePlace(course, place, ++size);
        coursePlaceRepository.save(coursePlace);
    }

    // 코스 수정(삭제)
    @Transactional
    public void updateCourseDelete (Long courseId, CourseUpdateAddDelRequestDto dto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        List<CoursePlace> coursePlaces = course.getCoursePlaces();

        int order = 1;
        for (CoursePlace cp: coursePlaces) {
            if (cp.getPlace().getId().equals(dto.getPlaceId())) {
                courseRepository.deleteCoursePlaceQueryDSL(courseId, cp);
            } else {
                cp.setOrders(order++);
                coursePlaceRepository.save(cp);
            }
        }
    }

    // 코스 수정(순서)
    @Transactional
    public void updateCourseOrders (Long courseId, CourseUpdateOrdersRequestDto dto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        List<CoursePlace> coursePlaces = course.getCoursePlaces();

        HashMap<Long, CoursePlace> map = new HashMap<>();

        for (CoursePlace cp : coursePlaces) {
            map.put(cp.getPlace().getId(), cp);
        }

        int orders = 1;
        for (Long placeId: dto.getPlaces()) {
            CoursePlace coursePlace = map.get(placeId);
            coursePlace.setOrders(orders++);
            coursePlaceRepository.save(coursePlace);
        }
    }
}
