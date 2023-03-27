package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.GugunDto;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.User;
import com.cosmos.back.model.UserPlace;
import com.cosmos.back.model.place.Gugun;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.model.place.Sido;
import com.cosmos.back.repository.place.GugunRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.place.SidoRepository;
import com.cosmos.back.repository.place.UserPlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
class PlaceServiceTest {

    @MockBean
    private SidoRepository sidoRepository;

    @MockBean
    private GugunRepository gugunRepository;

    @SpyBean
    private PlaceRepository placeRepository;

    @SpyBean
    private UserRepository userRepository;

    @Autowired
    private UserPlaceRepository userPlaceRepository;

    @Autowired
    private PlaceService placeService;

    @Test
    @DisplayName("시/도 리스트 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void listSidoServiceTest() throws Exception {
        //given
        List<Sido> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Sido.builder().sidoCode("testCode").sidoName("testName").build());
        }
        when(sidoRepository.findAll()).thenReturn(list);

        //when
        List<Sido> sidoList = placeService.listSido();

        //then
        assertThat(sidoList.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("구/군 리스트 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void listGugunServiceTest() throws Exception {
        //given
        List<Gugun> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Gugun.builder().gugunCode("1").gugunName("gugunTest").build());
        }
        when(gugunRepository.findBysidoCode(anyInt())).thenReturn(list);

        //when
        List<GugunDto> gugunDtoList = placeService.listGugun(1);

        System.out.println("list = " + list);
        System.out.println("gugunDtoList = " + gugunDtoList);
        //then
        assertThat(gugunDtoList.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("장소 검색 자동 완성 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void autoCompleteSearchPlacesByNameTest() throws Exception {
        //given
        List<AutoCompleteResponseDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            list.add(AutoCompleteResponseDto.builder().placeId(1L).name("nameTest").address("addressTest").thumbNailUrl("thumbNailUrlTest").type("type").build());
        }
        //when
        when(placeRepository.findPlaceListByNameAutoCompleteQueryDsl(anyString())).thenReturn(list);

        //then
        List<AutoCompleteResponseDto> test = placeService.autoCompleteSearchPlacesByName("Test");
        assertThat(test.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("장소 찜하기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void likePlaceTest() throws Exception {
        //given
        Place place = Place.builder().userPlaces(new ArrayList<>()).name("place").build();
        placeRepository.save(place);
        User user = User.builder().userPlaces(new ArrayList<>()).userName("user").build();
        userRepository.save(user);

        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        //when
        Map<String, Long> map = placeService.likePlace(1L, 1L);

        //then
        assertEquals(map.get("user"), user.getUserSeq());
        assertEquals(map.get("place"), place.getId());
    }

//
//    @MockBean
//    private PlaceRepository placeRepository;
//
//
//    @Test
//    @DisplayName("PlaceService 문화생활정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
//    public void 문화생활상세정보받아오기() throws Exception {
//
//        CultureResponseDto mockDto = CultureResponseDto.builder().name("문화생활").build();
//
//        // mocking
//        when(placeRepository.findCultureByPlaceIdQueryDsl(anyLong()))
//                .thenReturn(mockDto);
//
//        CultureResponseDto dto = placeService.detailCulture(1L);
//
//        assertEquals(dto.getName(), "문화생활");
//    }
//
//    @Test
//    @DisplayName("PlaceService 식당정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
//    public void 식당상세정보받아오기() throws Exception {
//
//        RestaurantResponseDto mockDto = RestaurantResponseDto.builder().name("식당").build();
//
//        // mocking
//        when(placeRepository.findRestaurantByPlaceIdQueryDsl(anyLong()))
//                .thenReturn(mockDto);
//
//        RestaurantResponseDto dto = placeService.detailRestaurant(1L);
//
//        assertEquals(dto.getName(), "식당");
//    }
//
//    @Test
//    @DisplayName("PlaceService 카페정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
//    public void 카페상세정보받아오기() throws Exception {
//
//        CafeResponseDto mockDto = CafeResponseDto.builder().name("카페").build();
//
//        // mocking
//        when(placeRepository.findCafeByPlaceIdQueryDsl(anyLong()))
//                .thenReturn(mockDto);
//
//        CafeResponseDto dto = placeService.detailCafe(1L);
//
//        assertEquals(dto.getName(), "카페");
//    }
//
//    @Test
//    @DisplayName("PlaceService 관광정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
//    public void 관광상세정보받아오기() throws Exception {
//
//        TourResponseDto mockDto = TourResponseDto.builder().name("관광").build();
//
//        // mocking
//        when(placeRepository.findTourByPlaceIdQueryDsl(anyLong()))
//                .thenReturn(mockDto);
//
//        TourResponseDto dto = placeService.detailTour(1L);
//
//        assertEquals(dto.getName(), "관광");
//    }
//
//    @Test
//    @DisplayName("PlaceService 레져정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
//    public void 레져상세정보받아오기() throws Exception {
//
//        LeisureResponseDto mockDto = LeisureResponseDto.builder().name("레져").build();
//
//        // mocking
//        when(placeRepository.findLeisureByPlaceIdQueryDsl(anyLong()))
//                .thenReturn(mockDto);
//
//        LeisureResponseDto dto = placeService.detailLeisure(1L);
//
//        assertEquals(dto.getName(), "레져");
//    }
//
//    @Test
//    @DisplayName("PlaceService 쇼핑정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
//    public void 쇼핑상세정보받아오기() throws Exception {
//
//        ShoppingResponseDto mockDto = ShoppingResponseDto.builder().name("쇼핑").build();
//
//        // mocking
//        when(placeRepository.findShoppingByPlaceIdQueryDsl(anyLong()))
//                .thenReturn(mockDto);
//
//        ShoppingResponseDto dto = placeService.detailShopping(1L);
//
//        assertEquals(dto.getName(), "쇼핑");
//    }
//
//    @Test
//    @DisplayName("PlaceService 숙소정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
//    public void 숙소상세정보받아오기() throws Exception {
//
//        AccommodationResponseDto mockDto = AccommodationResponseDto.builder().name("숙소").build();
//
//        // mocking
//        when(placeRepository.findAccommodationByPlaceIdQueryDsl(anyLong()))
//                .thenReturn(mockDto);
//
//        AccommodationResponseDto dto = placeService.detailAccommodation(1L);
//
//        assertEquals(dto.getName(), "숙소");
//    }
//
//    @Test
//    @DisplayName("PlaceService 축제정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // service가 repository를 잘 호출 하는지, 알맞는 Response Dto를 가져오는지 확인
//    public void 축제상세정보받아오기() throws Exception {
//
//        FestivalResponseDto mockDto = FestivalResponseDto.builder().name("축제").build();
//
//        // mocking
//        when(placeRepository.findFestivalByPlaceIdQueryDsl(anyLong()))
//                .thenReturn(mockDto);
//
//        FestivalResponseDto dto = placeService.detailFestival(1L);
//
//        assertEquals(dto.getName(), "축제");
//    }
}