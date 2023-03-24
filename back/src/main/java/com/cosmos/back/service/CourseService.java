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
    public CourseResponseDto createCourse(CourseRequestDto dto) {
        Long userSeq = dto.getUserSeq();

        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        // 데이트코스 저장
        Course course = Course.createCourse(user);
        courseRepository.save(course);

        List<Place> places = new ArrayList<>();

        for (String category : dto.getCategories()) {
            places.add(chooseOne(category, dto.getSido(), dto.getGugun()));
        }

        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setCourseId(course.getId());

        // 순서
        int orders = 1;
        for (Place p : places) {
            CoursePlace coursePlace = CoursePlace.createCoursePlace(course, p, orders);

            SimplePlaceDto placeDto = new SimplePlaceDto();

            placeDto.setPlaceId(p.getId());
            placeDto.setPlaceName(p.getName());
            placeDto.setLatitude(p.getLatitude());
            placeDto.setLongitude(p.getLongitude());
            placeDto.setThumbNailUrl(p.getThumbNailUrl());
            placeDto.setAddress(p.getAddress());
            placeDto.setOverview(p.getDetail());
            placeDto.setOrders(orders++);

            Double score = placeRepository.findScoreByPlaceIdQueryDsl(p.getId());
            placeDto.setScore(score);

            courseResponseDto.getPlaces().add(placeDto);

            coursePlaceRepository.save(coursePlace);
        }

        return courseResponseDto;
    }

    public Place chooseOne(String type, String sido, String gugun) {
        List<Place> places = placeRepository.findAllByTypeAndSidoAndGugun(type, sido, gugun);

        // 빅데이터 알고리즘
        return places.get(0);
    }

    // 코스 찜
    @Transactional
    public Long likeCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        course.setWish(true);

        return course.getId();
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
     * 내 찜한 코스 보기
     */
    public List<CourseResponseDto> listLikeCourse(Long userSeq) {
        // CourseResponseDto의 name, date, courseId, orders를 가져온다.
        List<CourseResponseDto> list = courseRepository.findAllCourseByUserSeq(userSeq);

        for (CourseResponseDto dto : list) {
            List<SimplePlaceDto> places = new ArrayList<>();

            // 해당 코스들의 placeId와 courseId를 가져온다.
            List<CoursePlace> coursePlaces = coursePlaceRepository.findAllByCourseId(dto.getCourseId());

            for (CoursePlace coursePlace : coursePlaces) {
                // 해당 placeId로 평점을 제외한 나머지 것들을 가져온다.
                SimplePlaceDto place = placeRepository.findSimplePlaceDtoByPlaceIdQueryDsl(coursePlace.getPlace().getId(), coursePlace.getCourse().getId());

                // 해당 SimplePlaceDto에 평점을 가져온다.
                Double score = placeRepository.findScoreByPlaceIdQueryDsl(coursePlace.getPlace().getId());

                place.setScore(score);

                places.add(place);
            }

            dto.setPlaces(places);
        }

        return list;
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
