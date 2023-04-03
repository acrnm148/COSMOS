package com.cosmos.back.service;

import com.cosmos.back.dto.SimplePlaceDto;
import com.cosmos.back.dto.request.*;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.model.*;
import com.cosmos.back.model.place.*;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.review.ReviewRepository;
import com.cosmos.back.repository.reviewplace.ReviewPlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import reactor.util.LinkedMultiValueMap;
import reactor.util.MultiValueMap;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class CourseService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final CourseRepository courseRepository;
    private final CoursePlaceRepository coursePlaceRepository;
    private final ReviewRepository reviewRepository;
    private final ObjectMapper objectMapper;

    // 코스 생성(추천 알고리즘)
    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto dto) throws JsonProcessingException {
        // 1. 데이트 코스 생성 후 저장
        Course course = saveCourse(dto.getUserSeq());

        // Review 개수 파악
        List<Review> reviews = reviewRepository.findReviewByUserSeq(dto.getUserSeq());

        // 20개 이하이면 컨텐츠 기반으로 리뷰 작성
        if (reviews.size() < 20) {
            // 2. 카테고리별 장소 가져오기
            List<Place> places = selectPlaces(dto.getCategories(), dto.getSido(), dto.getGugun(), dto.getUserSeq());

            // 3. return 해줄 CourseResponseDto 생성 후 courseId 넣기
            CourseResponseDto courseResponseDto = new CourseResponseDto();
            courseResponseDto.setCourseId(course.getId());

            // 4. CourseResponseDto에 midLatitude와 midLongitude 넣기
            saveMidLatitudeAndMidLongitude(courseResponseDto, places);

            // 5. CourseResponseDto 안의 List<SimplePlaceDto> 값 채우기
            List<Long> coursePlaceIds = saveSimplePlaceDtoList(course, courseResponseDto, places);

            return courseResponseDto;
        } else {
            // Django로 협업 필터링을 통한 알고리즘 장소 추천 받기
            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeader를 setting하기
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            // Body Setting
            CourseRequestDto body = new CourseRequestDto();
            body.setCategories(dto.getCategories());
            body.setSido(dto.getSido());
            body.setGugun(dto.getGugun());

            // HttpEntity 생성
            HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

            // API 호출
            String url = "http://j8e104.p.ssafy.io:8000/django/CF/algorithm/userSeq/" + dto.getUserSeq() + "/";
            ResponseEntity<String> responseData = restTemplate.postForEntity(url, requestMessage, String.class);
            List list = new Gson().fromJson(responseData.getBody(), List.class);

            // 연관 Place 찾아서 List로 만들어주기
            List<Place> places = new ArrayList<>();
            for (Object placeObject: list) {
                String value = String.valueOf(placeObject);
                Long placeId = Long.valueOf(value.substring(0, value.length() - 2));
                Place place = placeRepository.findById(placeId).orElseThrow(() -> new NoSuchElementException("no such data"));
                places.add(place);
            }

            // 3. return 해줄 CourseResponseDto 생성 후 courseId 넣기
            CourseResponseDto courseResponseDto = new CourseResponseDto();
            courseResponseDto.setCourseId(course.getId());

            // 4. CourseResponseDto에 midLatitude와 midLongitude 넣기
            saveMidLatitudeAndMidLongitude(courseResponseDto, places);

            // 5. CourseResponseDto 안의 List<SimplePlaceDto> 값 채우기
            List<Long> coursePlaceIds = saveSimplePlaceDtoList(course, courseResponseDto, places);

            return courseResponseDto;
        }
    }

    // 코스 생성(사용자 생성)
    @Transactional
    public Long createCourseByUser(Long userSeq, CouserUesrRequestDto dto) {
        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Course course = Course.createCourseByUser(user, dto.getName());
        course.setWish(true);

        courseRepository.save(course);

        int orders = 1;
        for (Long placeId : dto.getPlaceIds()) {
            Place place = placeRepository.findById(placeId).orElseThrow(() -> new IllegalArgumentException("no such data"));
            CoursePlace coursePlace = CoursePlace.createCoursePlace(course, place, orders++);
            coursePlaceRepository.save(coursePlace);
        }

        return course.getId();
    }

    // 데이트 코스 생성 후 저장
    @Transactional
    public Course saveCourse(Long userSeq) {
        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Course course = Course.createCourse(user);
        courseRepository.save(course);

        return course;
    }

    // 카테고리별 장소 가져오기
    public List<Place> selectPlaces (List<String> categories, String sido, String gugun, Long userSeq) {
        List<Place> places = new ArrayList<>();
        List<Long> placeIds = new ArrayList<>();

        for (String category : categories) {
            boolean isOverlapped = false;

            int count = 0;
            outer: while(true) {
                isOverlapped = false;
                Place place = chooseOne(category, sido, gugun, userSeq);
                if (count > 3 && placeIds.contains(place.getId())) {
                    while (true) {
                        place = chooseOneWithInAll(category, sido, gugun);
                        if (!placeIds.contains(place.getId())) {
                            places.add(place);
                            placeIds.add(place.getId());
                            break outer;
                        }
                    }
                }

                if (placeIds.contains(place.getId())) {
                    isOverlapped = true;
                }

                if (!isOverlapped) {
                    places.add(place);
                    placeIds.add(place.getId());
                    count = 0;
                    break;
                }

                count++;
            }
        }

        return places;
    }

    public void saveMidLatitudeAndMidLongitude(CourseResponseDto courseResponseDto, List<Place> places) {
        Double sumLatitude = 0.0;
        Double sumLongitude = 0.0;
        int count = 0;

        for (Place place : places) {
            if (place.getLatitude() != null && place.getLongitude() != null) {
                sumLatitude += Double.parseDouble(place.getLatitude());
                sumLongitude += Double.parseDouble(place.getLongitude());
                count++;
            }
        }

        courseResponseDto.setMidLatitude(sumLatitude / count);
        courseResponseDto.setMidLongitude(sumLongitude / count);
    }

    // CourseResponseDto 안의 List<SimplePlaceDto> 값 채우기
    @Transactional
    public List<Long> saveSimplePlaceDtoList(Course course, CourseResponseDto courseResponseDto, List<Place> places) {
        List<Long> coursePlaceIds = new ArrayList<>();

        // 순서
        int orders = 1;
        for (Place place : places) {
            CoursePlace coursePlace = CoursePlace.createCoursePlace(course, place, orders);

            SimplePlaceDto placeDto = new SimplePlaceDto();

            placeDto.setPlaceId(place.getId());
            placeDto.setName(place.getName());
            placeDto.setLatitude(place.getLatitude());
            placeDto.setLongitude(place.getLongitude());
            placeDto.setThumbNailUrl(place.getThumbNailUrl());
            placeDto.setAddress(place.getAddress());
            placeDto.setDetail(place.getDetail());
            placeDto.setOrders(orders++);
            placeDto.setType(place.getType());

            Double score = placeRepository.findScoreByPlaceIdQueryDsl(place.getId());
            placeDto.setScore(score);

            courseResponseDto.getPlaces().add(placeDto);

            coursePlaceRepository.save(coursePlace);
            coursePlaceIds.add(coursePlace.getId());
        }

        return coursePlaceIds;
    }

    public Place chooseOneWithInAll(String type, String sido, String gugun) {
        List<Place> places = placeRepository.findAllByTypeAndSidoAndGugun(type, sido, gugun);

        Integer size = places.size();

        Integer randomNum = (int) (Math.random() * size);

        return places.get(randomNum);
    }

    public Place chooseOne(String type, String sido, String gugun, Long userSeq) {
        User user = userRepository.findByUserSeq(userSeq);

        String type1 = user.getType1();
        String type2 = user.getType2();

        // EAT -> spo

        String typeTransformed1 = "";
        String typeTransformed2 = "";

        for (int i = 0; i < type1.length(); i++) {
            if (type1.charAt(i) == 'E') {
                typeTransformed1 += 's';
            } else if (type1.charAt(i) == 'A') {
                typeTransformed1 += 'p';
            } else if (type1.charAt(i) == 'T') {
                typeTransformed1 += 'o';
            } else if (type1.charAt(i) == 'J') {
                typeTransformed1 += 'd';
            } else if (type1.charAt(i) == 'O') {
                typeTransformed1 += 'f';
            } else if (type1.charAt(i) == 'Y') {
                typeTransformed1 += 'i';
            }
        }

        for (int i = 0; i < type2.length(); i++) {
            if (type2.charAt(i) == 'E') {
                typeTransformed2 += 's';
            } else if (type2.charAt(i) == 'A') {
                typeTransformed2 += 'p';
            } else if (type2.charAt(i) == 'T') {
                typeTransformed2 += 'o';
            } else if (type2.charAt(i) == 'J') {
                typeTransformed2 += 'd';
            } else if (type2.charAt(i) == 'O') {
                typeTransformed2 += 'f';
            } else if (type2.charAt(i) == 'Y') {
                typeTransformed2 += 'i';
            }
        }

        List<Place> places1 = placeRepository.findAllByTypeAndSidoAndGugunT(type, sido, gugun, typeTransformed1);
        List<Place> places2 = placeRepository.findAllByTypeAndSidoAndGugunT(type, sido, gugun, typeTransformed2);

        List<Place> places = new ArrayList<>();

        for (int i = 0; i < places1.size(); i++) {
            places.add(places1.get(i));
        }

        for (int i = 0; i < places2.size(); i++) {
            places.add(places2.get(i));
        }

        Integer size = places.size();

        if (size == 0) {
            places = placeRepository.findAllByTypeAndSidoAndGugun(type, sido, gugun);
            size = places.size();
        }

        Integer randomNum = (int) (Math.random() * size);

        // 빅데이터 알고리즘
        return places.get(randomNum);
    }

    // 코스 찜
    @Transactional
    public Map<String, String> likeCourse(Long courseId, String name) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        course.setWish(true);
        course.setName(name);

        Map<String, String> map = new HashMap<>();
        map.put("courseId", Long.toString(course.getId()));
        map.put("wish", Boolean.toString(course.getWish()));
        map.put("name", name);

        return map;
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
     * 내 코스 상세보기
     */
    public CourseResponseDto getMyCourseDetail(Long courseId) {
        CourseResponseDto courseResponseDto = courseRepository.findByCourseIdQueryDSL(courseId);

        List<SimplePlaceDto> places = new ArrayList<>();

        // 해당 코스들의 placeId와 courseId를 가져온다.
        List<CoursePlace> coursePlaces = coursePlaceRepository.findAllByCourseId(courseResponseDto.getCourseId());

        for (CoursePlace coursePlace : coursePlaces) {
            // 해당 placeId로 평점을 제외한 나머지 것들을 가져온다.
            SimplePlaceDto place = placeRepository.findSimplePlaceDtoByPlaceIdQueryDsl(coursePlace.getPlace().getId(), coursePlace.getCourse().getId());

            // 해당 SimplePlaceDto에 평점을 가져온다.
            Double score = placeRepository.findScoreByPlaceIdQueryDsl(coursePlace.getPlace().getId());

            place.setScore(score);

            places.add(place);
        }

        courseResponseDto.setPlaces(places);

        return courseResponseDto;
    }

    // 코스 수정
    @Transactional
    public Long updateCourse (Long courseId, CourseUpdateRequstDto dto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        course.setName(dto.getName());

        List<CoursePlace> coursePlaces = coursePlaceRepository.findAllByCourseId(courseId);

        boolean[] coursePlacesArray = new boolean[coursePlaces.size()];
        boolean[] dtoPlacesArray = new boolean[dto.getPlaceIds().size()];

        for (int i = 0; i < coursePlacesArray.length; i++) {
            for (int j = 0; j < dtoPlacesArray.length; j++) {
                if (coursePlaces.get(i).getId().equals(dto.getPlaceIds().get(j))) {
                    coursePlacesArray[i] = true;
                    dtoPlacesArray[j] = true;
                    break;
                }
            }
        }

        for (int i = 0; i < coursePlacesArray.length; i++) {
            if (!coursePlacesArray[i]) {
                coursePlaceRepository.deleteById(coursePlaces.get(i).getId());
            }
        }

        int orders = 1;
        for (int j = 0; j < dtoPlacesArray.length; j++) {
            if (dtoPlacesArray[j]) {
                CoursePlace coursePlace = coursePlaceRepository.findByCourseIdAndPlaceId(courseId, dto.getPlaceIds().get(j));
                coursePlace.setOrders(orders++);
            } else {
                Place place = placeRepository.findById(dto.getPlaceIds().get(j)).orElseThrow(() -> new IllegalArgumentException("no such data"));
                CoursePlace coursePlace = CoursePlace.createCoursePlace(course, place, orders++);
                coursePlaceRepository.save(coursePlace);
            }
        }

        return course.getId();
    }
}
