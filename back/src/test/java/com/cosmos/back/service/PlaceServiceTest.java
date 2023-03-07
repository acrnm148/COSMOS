package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.repository.place.PlaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
class PlaceServiceTest {

    @MockBean
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceService placeService;

    @Test
    @DisplayName("PlaceService 문화생활정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
    public void 문화생활상세정보받아오기() throws Exception {

        CulturesResponseDto mockDto = CulturesResponseDto.builder().name("문화생활").build();

        // mocking
        when(placeRepository.findCultureByPlaceIdQueryDsl(anyLong()))
                .thenReturn(mockDto);

        CulturesResponseDto dto = placeService.detailCulture(1L);

        assertEquals(dto.getName(), "문화생활");
    }

    @Test
    @DisplayName("PlaceService 식당정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
    public void 식당상세정보받아오기() throws Exception {

        RestaurantResponseDto mockDto = RestaurantResponseDto.builder().name("식당").build();

        // mocking
        when(placeRepository.findRestaurantByPlaceIdQueryDsl(anyLong()))
                .thenReturn(mockDto);

        RestaurantResponseDto dto = placeService.detailRestaurant(1L);

        assertEquals(dto.getName(), "식당");
    }

    @Test
    @DisplayName("PlaceService 카페정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
    public void 카페상세정보받아오기() throws Exception {

        CafeResponseDto mockDto = CafeResponseDto.builder().name("카페").build();

        // mocking
        when(placeRepository.findCafeByPlaceIdQueryDsl(anyLong()))
                .thenReturn(mockDto);

        CafeResponseDto dto = placeService.detailCafe(1L);

        assertEquals(dto.getName(), "카페");
    }

    @Test
    @DisplayName("PlaceService 관광정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
    public void 관광상세정보받아오기() throws Exception {

        TourResponseDto mockDto = TourResponseDto.builder().name("관광").build();

        // mocking
        when(placeRepository.findTourByPlaceIdQueryDsl(anyLong()))
                .thenReturn(mockDto);

        TourResponseDto dto = placeService.detailTour(1L);

        assertEquals(dto.getName(), "관광");
    }

    @Test
    @DisplayName("PlaceService 레져정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
    public void 레져상세정보받아오기() throws Exception {

        LeisuresResponseDto mockDto = LeisuresResponseDto.builder().name("레져").build();

        // mocking
        when(placeRepository.findLeisureByPlaceIdQueryDsl(anyLong()))
                .thenReturn(mockDto);

        LeisuresResponseDto dto = placeService.detailLeisure(1L);

        assertEquals(dto.getName(), "레져");
    }

    @Test
    @DisplayName("PlaceService 쇼핑정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
    public void 쇼핑상세정보받아오기() throws Exception {

        ShoppingResponseDto mockDto = ShoppingResponseDto.builder().name("쇼핑").build();

        // mocking
        when(placeRepository.findShoppingByPlaceIdQueryDsl(anyLong()))
                .thenReturn(mockDto);

        ShoppingResponseDto dto = placeService.detailShopping(1L);

        assertEquals(dto.getName(), "쇼핑");
    }

    @Test
    @DisplayName("PlaceService 숙소정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
    public void 숙소상세정보받아오기() throws Exception {

        AccommodationResponseDto mockDto = AccommodationResponseDto.builder().name("숙소").build();

        // mocking
        when(placeRepository.findAccommodationByPlaceIdQueryDsl(anyLong()))
                .thenReturn(mockDto);

        AccommodationResponseDto dto = placeService.detailAccommodation(1L);

        assertEquals(dto.getName(), "숙소");
    }

    @Test
    @DisplayName("PlaceService 축제정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
    public void 축제상세정보받아오기() throws Exception {

        FestivalResponseDto mockDto = FestivalResponseDto.builder().name("축제").build();

        // mocking
        when(placeRepository.findFestivalByPlaceIdQueryDsl(anyLong()))
                .thenReturn(mockDto);

        FestivalResponseDto dto = placeService.detailFestival(1L);

        assertEquals(dto.getName(), "축제");
    }
}