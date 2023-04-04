//package com.cosmos.back.repository;
//
//import com.cosmos.back.annotation.EnableMockMvc;
//import com.cosmos.back.config.TestConfig;
//import com.cosmos.back.dto.SimplePlaceDto;
//import com.cosmos.back.dto.response.place.*;
//import com.cosmos.back.model.*;
//import com.cosmos.back.model.place.*;
//import com.cosmos.back.repository.course.CoursePlaceRepository;
//import com.cosmos.back.repository.course.CourseRepository;
//import com.cosmos.back.repository.place.PlaceRepository;
//import com.cosmos.back.repository.place.UserPlaceRepository;
//import com.cosmos.back.repository.review.ReviewRepository;
//import com.cosmos.back.repository.reviewplace.ReviewPlaceRepository;
//import com.cosmos.back.repository.user.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@Import({TestConfig.class})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@EnableMockMvc
//class PlaceRepositoryTest {
//
//    @Autowired
//    private PlaceRepository placeRepository;
//
//    @Autowired
//    private UserPlaceRepository userPlaceRepository;
//
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @Autowired
//    private CoursePlaceRepository coursePlaceRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ReviewPlaceRepository reviewPlaceRepository;
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//
//    @Test
//    @DisplayName("PlaceRepository 쇼핑상세정보가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 쇼핑상세정보가져오기() throws Exception {
//        Shopping shopping = Shopping.builder().shoppingList("판매품목1").build();
//        placeRepository.save(shopping);
//
//        ShoppingResponseDto dto = placeRepository.findShoppingByPlaceIdQueryDsl(shopping.getId());
//        assertEquals(dto.getPlaceId(), shopping.getId());
//        assertEquals(dto.getShoppingList(), "판매품목1");
//    }
//
//    @Test
//    @DisplayName("PlaceRepository 식당상세정보가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 식당상세정보가져오기() throws Exception {
//        Restaurant restaurant = Restaurant.builder().takeoutYn("포장 가능").build();
//        placeRepository.save(restaurant);
//
//        RestaurantResponseDto dto = placeRepository.findRestaurantByPlaceIdQueryDsl(restaurant.getId());
//        assertEquals(dto.getPlaceId(), restaurant.getId());
//        assertEquals(dto.getTakeoutYn(), "포장 가능");
//    }
//
//    @Test
//    @DisplayName("PlaceRepository 카페상세정보가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 카페상세정보가져오기() throws Exception {
//        Cafe cafe = Cafe.builder().playground("놀이방 있음").build();
//        placeRepository.save(cafe);
//
//        CafeResponseDto dto = placeRepository.findCafeByPlaceIdQueryDsl(cafe.getId());
//        System.out.println(cafe.getId());
//        System.out.println(dto.getPlayground());
//        assertEquals(dto.getPlaceId(), cafe.getId());
//        assertEquals(dto.getPlayground(), "놀이방 있음");
//    }
//
//    @Test
//    @DisplayName("PlaceRepository 문화생활상세정보가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 문화생활상세정보가져오기() throws Exception {
//        Culture culture = Culture.builder().parkingYn("주차가능").build();
//        placeRepository.save(culture);
//
//        CultureResponseDto dto = placeRepository.findCultureByPlaceIdQueryDsl(culture.getId());
//        assertEquals(dto.getPlaceId(), culture.getId());
//        assertEquals(dto.getParkingYn(), "주차가능");
//    }
//
//    @Test
//    @DisplayName("PlaceRepository 레져상세정보가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 레져상세정보가져오기() throws Exception {
//        Leisure leisure = Leisure.builder().acceptablePeople("500명").build();
//        placeRepository.save(leisure);
//
//        LeisureResponseDto dto = placeRepository.findLeisureByPlaceIdQueryDsl(leisure.getId());
//        assertEquals(dto.getPlaceId(), leisure.getId());
//        assertEquals(dto.getAcceptablePeople(), "500명");
//    }
//
//    @Test
//    @DisplayName("PlaceRepository 축제상세정보가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 축제상세정보가져오기() throws Exception {
//        Festival festival = Festival.builder().startDate("20230328").endDate("20201010").build();
//        placeRepository.save(festival);
//
//        FestivalResponseDto dto = placeRepository.findFestivalByPlaceIdQueryDsl(festival.getId());
//        assertEquals(dto.getPlaceId(), festival.getId());
//        assertEquals(dto.getEndDate(), "20201010");
//    }
//
//    @Test
//    @DisplayName("PlaceRepository 숙박상세정보가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 숙박상세정보가져오기() throws Exception {
//        Accommodation accommodation = Accommodation.builder().bbqYn("바베큐가능").build();
//        placeRepository.save(accommodation);
//
//        AccommodationResponseDto dto = placeRepository.findAccommodationByPlaceIdQueryDsl(accommodation.getId());
//        assertEquals(dto.getPlaceId(), accommodation.getId());
//        assertEquals(dto.getBbqYn(), "바베큐가능");
//    }
//
//    @Test
//    @DisplayName("PlaceRepository 관광상세정보가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 관광상세정보가져오기() throws Exception {
//        Tour tour = Tour.builder().insideYn("실외").build();
//        placeRepository.save(tour);
//
//        TourResponseDto dto = placeRepository.findTourByPlaceIdQueryDsl(tour.getId());
//        assertEquals(dto.getPlaceId(), tour.getId());
//        assertEquals(dto.getInsideYn(), "실외");
//    }
//
//    @Test
//    @DisplayName("PlaceRepository 장소 찜 확인하기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void findPlaceLikeByPlaceIdUserSeqQueryDslTest() throws Exception {
//        //given
//        User userLike = User.builder().userName("testLikeUser").build();
//        userRepository.save(userLike);
//        User userUnLike = User.builder().userName("testUnLikeUser").build();
//        userRepository.save(userUnLike);
//        Place place = Place.builder().name("testPlace").build();
//        placeRepository.save(place);
//        UserPlace userPlace = UserPlace.builder().place(place).user(userLike).build();
//        userPlaceRepository.save(userPlace);
//
//        //when
//        boolean like = placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(place.getId(), userLike.getUserSeq());
//        boolean unLike = placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(place.getId(), userUnLike.getUserSeq());
//
//        //then
//        assertEquals(like, true);
//        assertEquals(unLike, false);
//    }
//
//    @Test
//    @DisplayName("PlaceRepository 시/도, 구/군, 검색어, 검색필터를 통한 장소 검색")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void findPlaceListBySidoGugunTextFilterQueryDslTest() throws Exception {
//
//        //given
//        Place place = Place.builder().address("부산광역시 강서구").name("갈비").type("restaurant").build();
//        Festival festival = Festival.builder().startDate("20230330").address("부산광역시 해운대구").name("축제").type("festival").build();
//        placeRepository.save(place);
//        placeRepository.save(festival);
//
//        Review review = Review.builder().score(5).build();
//        Review reviewFestival = Review.builder().score(5).build();
//        reviewRepository.save(review);
//        reviewRepository.save(reviewFestival);
//
//        ReviewPlace reviewPlace = ReviewPlace.builder().place(place).review(review).build();
//        ReviewPlace reviewPlaceFestival = ReviewPlace.builder().place(festival).review(reviewFestival).build();
//        reviewPlaceRepository.save(reviewPlace);
//        reviewPlaceRepository.save(reviewPlaceFestival);
//
//        //when
//        List<PlaceSearchListResponseDto> test1 = placeRepository.findPlaceListBySidoGugunTextFilterQueryDsl(1L, "부산광역시", "강서구", "갈비", "restaurant", 10, 0);
//        List<PlaceSearchListResponseDto> test2 = placeRepository.findPlaceListBySidoGugunTextFilterQueryDsl(1L, "부산광역시", "해운대구", "축제", "festival", 10, 0);
//        List<PlaceSearchListResponseDto> test3 = placeRepository.findPlaceListBySidoGugunTextFilterQueryDsl(1L, null, null, "갈비", "restaurant", 10, 0);
//        List<PlaceSearchListResponseDto> test4 = placeRepository.findPlaceListBySidoGugunTextFilterQueryDsl(1L, "부산광역시", "강서구", null, null, 10, 0);
//
//        //then
//        assertEquals(test1.get(0).getPlaceId(), place.getId());
//        assertEquals(test2.get(0).getPlaceId(), festival.getId());
//        for (PlaceSearchListResponseDto dto: test3) {
//            assertEquals(dto.getType(), "restaurant");
//            assertThat(dto.getName()).contains("갈비");
//        }
//
//        assertThat(test4.get(0).getAddress().contains("부산"));
//    }
//
//    @Test
//    @DisplayName("장소 검색 자동 완성")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void findPlaceListByNameAutoCompleteQueryDslTest() throws Exception {
//        //given
//        Place place = Place.builder().name("searchTest").build();
//        placeRepository.save(place);
//
//        //when
//        List<AutoCompleteResponseDto> list = placeRepository.findPlaceListByNameAutoCompleteQueryDsl(place.getName());
//
//        //then
//        assertEquals(list.get(0).getName(), place.getName());
//    }
//
//    @Test
//    @DisplayName("장소별 별점 평균 가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void findScoreByPlaceIdQueryDslTest() throws Exception {
//        //given
//        Place place = Place.builder().name("testPlace").build();
//        placeRepository.save(place);
//
//        Review review1 = Review.builder().score(5).reviewPlaces(new ArrayList<>()).build();
//        Review review2 = Review.builder().score(1).reviewPlaces(new ArrayList<>()).build();
//        reviewRepository.save(review1);
//        reviewRepository.save(review2);
//
//        ReviewPlace reviewPlace1 = ReviewPlace.builder().review(review1).place(place).build();
//        ReviewPlace reviewPlace2 = ReviewPlace.builder().review(review2).place(place).build();
//        reviewPlaceRepository.save(reviewPlace1);
//        reviewPlaceRepository.save(reviewPlace2);
//
//        //when
//        Double result = placeRepository.findScoreByPlaceIdQueryDsl(place.getId());
//
//        //then
//        assertEquals(result, 3);
//    }
//
//    @Test
//    @DisplayName("시도, 구군, 타입별 장소 리스트 가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles={"SUPER"})
//    public void findAllByTypeAndSidoAndGugunTest() throws Exception{
//        //given
//        Place place = Place.builder().type("tour").address("부산광역시 강서구").build();
//        placeRepository.save(place);
//
//        //when
//        List<Place> result = placeRepository.findAllByTypeAndSidoAndGugun("tour", "부산광역시", "강서구");
//
//        //then
//        assertEquals(result.get(0).getType(), "tour");
//        assertThat(result.get(0).getAddress().contains("부산"));
//    }
//
//    @Test
//    @DisplayName("SimplePlaceDto 내용 가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles={"SUPER"})
//    public void findSimplePlaceDtoByPlaceIdQueryDslTest() throws Exception{
//        //given
//        User user = User.builder().build();
//        userRepository.save(user);
//        Place place = Place.builder().name("placeTest").build();
//        placeRepository.save(place);
//        Course course = Course.builder().coursePlaces(new ArrayList<>()).user(user).build();
//        courseRepository.save(course);
//        CoursePlace coursePlace = CoursePlace.builder().place(place).course(course).build();
//        coursePlaceRepository.save(coursePlace);
//
//        //when
//        SimplePlaceDto result = placeRepository.findSimplePlaceDtoByPlaceIdQueryDsl(place.getId(), course.getId());
//
//        //then
//        assertEquals(result.getPlaceId(), place.getId());
//
//    }
//
//    @Test
//    @DisplayName("SimplePlaceDto courseId를 통해 내용 가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles={"SUPER"})
//    public void findSimplePlaceDtoByCourseIdQueryDslTest() throws Exception{
//        //given
//        User user = User.builder().coupleId(1L).build();
//        userRepository.save(user);
//        Place place = Place.builder().name("placeTest").build();
//        placeRepository.save(place);
//        Course course = Course.builder().coursePlaces(new ArrayList<>()).user(user).build();
//        courseRepository.save(course);
//        CoursePlace coursePlace = CoursePlace.builder().place(place).course(course).build();
//        coursePlaceRepository.save(coursePlace);
//
//        //when
//        List<SimplePlaceDto> result = placeRepository.findSimplePlaceDtoByCourseIdQueryDsl(course.getId());
//
//        //then
//        assertEquals(result.get(0).getPlaceId(), place.getId());
//
//    }
//
//    @Test
//    @DisplayName("유저가 찜한 장소 삭제")
//    @WithMockUser(username = "테스트_최고관리자", roles={"SUPER"})
//    public void deleteUserPlaceQueryDslTest() throws Exception {
//        //given
//        User user = User.builder().coupleId(1L).build();
//        userRepository.save(user);
//        Place place = Place.builder().name("placeTest").build();
//        placeRepository.save(place);
//        UserPlace userPlace = UserPlace.builder().place(place).user(user).build();
//        userPlaceRepository.save(userPlace);
//
//        //when
//        Long result = userPlaceRepository.deleteUserPlaceQueryDsl(place.getId(), user.getUserSeq());
//
//        //then
//        assertThat(result).isEqualTo(1L);
//
//    }
//
//    @Test
//    @DisplayName("유저가 찜한 장소 찾기")
//    @WithMockUser(username = "테스트_최고관리자", roles={"SUPER"})
//    public void findLikePlacesTest() throws Exception {
//        //given
//        User user = User.builder().reviews(new ArrayList<>()).coupleId(1L).build();
//        userRepository.save(user);
//        Place place = Place.builder().name("placeTest").build();
//        placeRepository.save(place);
//        UserPlace userPlace = UserPlace.builder().place(place).user(user).build();
//        userPlaceRepository.save(userPlace);
//        Review review = Review.builder().user(user).score(5).build();
//        reviewRepository.save(review);
//        ReviewPlace reviewPlace = ReviewPlace.builder().place(place).review(review).build();
//        reviewPlaceRepository.save(reviewPlace);
//
//        //when
//        List<PlaceListResponseDto> result = userPlaceRepository.findLikePlaces(user.getUserSeq(), 10, 0);
//
//        //then
//        assertThat(result.size()).isEqualTo(1);
//
//
//    }
//
//}