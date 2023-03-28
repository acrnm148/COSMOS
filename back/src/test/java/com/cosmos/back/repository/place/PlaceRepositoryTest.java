package com.cosmos.back.repository.place;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.config.TestConfig;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.place.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({TestConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableMockMvc
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    @DisplayName("PlaceRepository 쇼핑상세정보가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 쇼핑상세정보가져오기() throws Exception {
        Shopping shopping = Shopping.builder().shoppingList("판매품목1").build();
        placeRepository.save(shopping);

        ShoppingResponseDto dto = placeRepository.findShoppingByPlaceIdQueryDsl(shopping.getId());
        assertEquals(dto.getPlaceId(), shopping.getId());
        assertEquals(dto.getShoppingList(), "판매품목1");
    }

    @Test
    @DisplayName("PlaceRepository 식당상세정보가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 식당상세정보가져오기() throws Exception {
        Restaurant restaurant = Restaurant.builder().takeoutYn("포장 가능").build();
        placeRepository.save(restaurant);

        RestaurantResponseDto dto = placeRepository.findRestaurantByPlaceIdQueryDsl(restaurant.getId());
        assertEquals(dto.getPlaceId(), restaurant.getId());
        assertEquals(dto.getTakeoutYn(), "포장 가능");
    }

    @Test
    @DisplayName("PlaceRepository 카페상세정보가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 카페상세정보가져오기() throws Exception {
        Cafe cafe = Cafe.builder().playground("놀이방 있음").build();
        placeRepository.save(cafe);

        CafeResponseDto dto = placeRepository.findCafeByPlaceIdQueryDsl(cafe.getId());
        System.out.println(cafe.getId());
        System.out.println(dto.getPlayground());
        assertEquals(dto.getPlaceId(), cafe.getId());
        assertEquals(dto.getPlayground(), "놀이방 있음");
    }

    @Test
    @DisplayName("PlaceRepository 문화생활상세정보가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 문화생활상세정보가져오기() throws Exception {
        Culture culture = Culture.builder().parkingYn("주차가능").build();
        placeRepository.save(culture);

        CultureResponseDto dto = placeRepository.findCultureByPlaceIdQueryDsl(culture.getId());
        assertEquals(dto.getPlaceId(), culture.getId());
        assertEquals(dto.getParkingYn(), "주차가능");
    }

    @Test
    @DisplayName("PlaceRepository 레져상세정보가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 레져상세정보가져오기() throws Exception {
        Leisure leisure = Leisure.builder().acceptablePeople("500명").build();
        placeRepository.save(leisure);

        LeisureResponseDto dto = placeRepository.findLeisureByPlaceIdQueryDsl(leisure.getId());
        assertEquals(dto.getPlaceId(), leisure.getId());
        assertEquals(dto.getAcceptablePeople(), "500명");
    }

//    @Test
//    @DisplayName("PlaceRepository 축제상세정보가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 축제상세정보가져오기() throws Exception {
//        Festival festival = Festival.builder().endDate("2020년 10월 10일").build();
//        placeRepository.save(festival);
//
//        FestivalResponseDto dto = placeRepository.findFestivalByPlaceIdQueryDsl(festival.getId());
//        assertEquals(dto.getPlaceId(), festival.getId());
//        assertEquals(dto.getEndDate(), "2020년 10월 10일");
//    }

    @Test
    @DisplayName("PlaceRepository 숙박상세정보가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 숙박상세정보가져오기() throws Exception {
        Accommodation accommodation = Accommodation.builder().bbqYn("바베큐가능").build();
        placeRepository.save(accommodation);

        AccommodationResponseDto dto = placeRepository.findAccommodationByPlaceIdQueryDsl(accommodation.getId());
        assertEquals(dto.getPlaceId(), accommodation.getId());
        assertEquals(dto.getBbqYn(), "바베큐가능");
    }

    @Test
    @DisplayName("PlaceRepository 관광상세정보가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 관광상세정보가져오기() throws Exception {
        Tour tour = Tour.builder().insideYn("실외").build();
        placeRepository.save(tour);

        TourResponseDto dto = placeRepository.findTourByPlaceIdQueryDsl(tour.getId());
        assertEquals(dto.getPlaceId(), tour.getId());
        assertEquals(dto.getInsideYn(), "실외");
    }
}