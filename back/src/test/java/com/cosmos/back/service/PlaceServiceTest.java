package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.GugunDto;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.Plan;
import com.cosmos.back.model.User;
import com.cosmos.back.model.UserPlace;
import com.cosmos.back.model.place.Gugun;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.model.place.Sido;
import com.cosmos.back.repository.place.GugunRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.place.SidoRepository;
import com.cosmos.back.repository.place.UserPlaceRepository;
import com.cosmos.back.repository.plan.PlanRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
@Transactional
class PlaceServiceTest {

    @MockBean
    private SidoRepository sidoRepository;

    @MockBean
    private GugunRepository gugunRepository;

    @SpyBean
    private PlaceRepository placeRepository;

    @SpyBean
    private UserRepository userRepository;

    @MockBean
    private UserPlaceRepository userPlaceRepository;

    @Autowired
    private PlaceService placeService;
    @Autowired
    private PlanRepository planRepository;

    @Test
    @DisplayName("찜한 장소 조회하기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findLikePlacesTest() throws Exception {
        //given
        List<PlaceListResponseDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(PlaceListResponseDto.builder().build());
        }
        when(userPlaceRepository.findLikePlaces(anyLong(), anyInt(), anyInt())).thenReturn(list);

        //when
        List<PlaceListResponseDto> likePlaces = placeService.findLikePlaces(1L, 10, 0);

        //then
        assertThat(likePlaces.size()).isEqualTo(5);

    }

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

        when(placeRepository.findById(1L))
                .thenReturn(Optional.of(place));

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        //when
        Map<String, Long> map = placeService.likePlace(1L, 1L);

        //then
        assertEquals(map.get("user"), user.getUserSeq());
        assertEquals(map.get("place"), place.getId());
//        try {
//            placeRepository.findById(null);
//        } catch (Exception e) {
//            System.out.println("e = " + e);
//            String message = e.getMessage();
//            System.out.println("message = " + message);
//        }
    }

    @Test
    @DisplayName("장소 삭제하기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void deleteeLikePlaceTest() throws Exception {
        //given
        when(userPlaceRepository.deleteUserPlaceQueryDsl(anyLong(), anyLong())).thenReturn(1L)
                .thenReturn(0L);

        //when
        Long deleteOk = placeService.deleteLikePlace(1L, 1L);

        //then
        assertEquals(deleteOk, 1L);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> placeService.deleteLikePlace(1L, 1L));
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 찜 입니다.");
    }

    @Test
    @DisplayName("장소 검색(시/도, 구/군, 검색어, 검색필터)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void searchPlacesBySidoGugunTextFilterTest() throws Exception {
        //given
        List<PlaceSearchListResponseDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            list.add(PlaceSearchListResponseDto.builder().placeId(new Long(i)).latitude("1").longitude("1").build());
        }
        List<PlaceSearchListResponseDto> listLatLongEmpty = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            listLatLongEmpty.add(PlaceSearchListResponseDto.builder().placeId(new Long(i)).build());
        }

        when(placeRepository
                .findPlaceListBySidoGugunTextFilterQueryDsl(1L, "sido", "gugun", "text", "filter", 10, 0))
                .thenReturn(list)
                .thenReturn(listLatLongEmpty);
        when(placeRepository
                .findPlaceListBySidoGugunTextFilterQueryDsl(1L, null, null, "text", "filter", 10, 0))
                .thenReturn(list)
                .thenReturn(listLatLongEmpty);
        when(placeRepository
                .findPlaceListBySidoGugunTextFilterQueryDsl(1L, "sido", "gugun", null, null, 10, 0))
                .thenReturn(list)
                .thenReturn(listLatLongEmpty);
        when(placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(anyLong(), anyLong()))
                .thenReturn(true)
                .thenReturn(false);

        //when
        PlaceFilterResponseDto placeFilterResponseDto = placeService.searchPlacesBySidoGugunTextFilter(1L, "sido", "gugun", "text", "filter", 10, 0);
        PlaceFilterResponseDto placeFilterResponseDto1 = placeService.searchPlacesBySidoGugunTextFilter(1L, "sido", "gugun", "text", "filter", 10, 0);
        PlaceFilterResponseDto placeFilterResponseDto2 = placeService.searchPlacesBySidoGugunTextFilter(1L, null, null, "text", "filter", 10, 0);
        PlaceFilterResponseDto placeFilterResponseDto3 = placeService.searchPlacesBySidoGugunTextFilter(1L, null, null, "text", "filter", 10, 0);
        PlaceFilterResponseDto placeFilterResponseDto4 = placeService.searchPlacesBySidoGugunTextFilter(1L, "sido", "gugun", null, null, 10, 0);
        PlaceFilterResponseDto placeFilterResponseDto5 = placeService.searchPlacesBySidoGugunTextFilter(1L, "sido", "gugun", null, null, 10, 0);

        //then
        assertEquals(placeFilterResponseDto.getPlaces().size(), 5);
        assertEquals(placeFilterResponseDto1.getPlaces().size(), 5);
        assertEquals(placeFilterResponseDto2.getPlaces().size(), 5);
        assertEquals(placeFilterResponseDto3.getPlaces().size(), 5);
        assertEquals(placeFilterResponseDto4.getPlaces().size(), 5);
        assertEquals(placeFilterResponseDto5.getPlaces().size(), 5);
    }

    @Test
    @DisplayName("관광 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void detailTourTest() throws Exception {
        //given
        TourResponseDto dto = TourResponseDto.builder().name("test").build();
        boolean execute = true;

        when(placeRepository.findTourByPlaceIdQueryDsl(anyLong())).thenReturn(dto)
                .thenReturn(null);
        when(placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(anyLong(), anyLong())).thenReturn(execute);

        //when
        TourResponseDto tourResponseOk = placeService.detailTour(1L, 1L);
        TourResponseDto tourResponseEmpty = placeService.detailTour(1L, 1L);

        //then
        assertEquals(tourResponseOk.getName(), dto.getName());
        assertNull(tourResponseEmpty);
    }

    @Test
    @DisplayName("축제 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void detailFestivalTest() throws Exception {
        //given
        FestivalResponseDto dto = FestivalResponseDto.builder().name("test").build();
        boolean execute = true;

        when(placeRepository.findFestivalByPlaceIdQueryDsl(anyLong())).thenReturn(dto)
                .thenReturn(null);
        when(placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(anyLong(), anyLong())).thenReturn(execute);

        //when
        FestivalResponseDto festivalResponseOk = placeService.detailFestival(1L, 1L);
        FestivalResponseDto festivalResponseEmpty = placeService.detailFestival(1L, 1L);

        //then
        assertEquals(festivalResponseOk.getName(), dto.getName());
        assertNull(festivalResponseEmpty);
    }

    @Test
    @DisplayName("숙박 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void detailAccommodationTest() throws Exception {
        //given
        AccommodationResponseDto dto = AccommodationResponseDto.builder().name("test").build();
        boolean execute = true;

        when(placeRepository.findAccommodationByPlaceIdQueryDsl(anyLong())).thenReturn(dto)
                .thenReturn(null);
        when(placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(anyLong(), anyLong())).thenReturn(execute);

        //when
        AccommodationResponseDto accommodationResponseOk = placeService.detailAccommodation(1L, 1L);
        AccommodationResponseDto accommodationResponseEmpty = placeService.detailAccommodation(1L, 1L);

        //then
        assertEquals(accommodationResponseOk.getName(), dto.getName());
        assertNull(accommodationResponseEmpty);
    }

    @Test
    @DisplayName("음식점 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void detailRestaurantTest() throws Exception {
        //given
        RestaurantResponseDto dto = RestaurantResponseDto.builder().name("test").build();
        boolean execute = true;

        when(placeRepository.findRestaurantByPlaceIdQueryDsl(anyLong())).thenReturn(dto)
                .thenReturn(null);
        when(placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(anyLong(), anyLong())).thenReturn(execute);

        //when
        RestaurantResponseDto restaurantResponseOk = placeService.detailRestaurant(1L, 1L);
        RestaurantResponseDto restaurantResponseEmpty = placeService.detailRestaurant(1L, 1L);

        //then
        assertEquals(restaurantResponseOk.getName(), dto.getName());
        assertNull(restaurantResponseEmpty);
    }

    @Test
    @DisplayName("카페 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void detailCafeTest() throws Exception {
        //given
        CafeResponseDto dto = CafeResponseDto.builder().name("test").build();
        boolean execute = true;

        when(placeRepository.findCafeByPlaceIdQueryDsl(anyLong())).thenReturn(dto)
                .thenReturn(null);
        when(placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(anyLong(), anyLong())).thenReturn(execute);

        //when
        CafeResponseDto cafeResponseOk = placeService.detailCafe(1L, 1L);
        CafeResponseDto cafeResponseEmpty = placeService.detailCafe(1L, 1L);

        //then
        assertEquals(cafeResponseOk.getName(), dto.getName());
        assertNull(cafeResponseEmpty);
    }

    @Test
    @DisplayName("쇼핑 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void detailShoppingTest() throws Exception {
        //given
        ShoppingResponseDto dto = ShoppingResponseDto.builder().name("test").build();
        boolean execute = true;

        when(placeRepository.findShoppingByPlaceIdQueryDsl(anyLong())).thenReturn(dto)
                .thenReturn(null);
        when(placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(anyLong(), anyLong())).thenReturn(execute);

        //when
        ShoppingResponseDto shoppingResponseOk = placeService.detailShopping(1L, 1L);
        ShoppingResponseDto shoppingResponseEmpty = placeService.detailShopping(1L, 1L);

        //then
        assertEquals(shoppingResponseOk.getName(), dto.getName());
        assertNull(shoppingResponseEmpty);
    }

    @Test
    @DisplayName("레포츠 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void detailLeisureTest() throws Exception {
        //given
        LeisureResponseDto dto = LeisureResponseDto.builder().name("test").build();
        boolean execute = true;

        when(placeRepository.findLeisureByPlaceIdQueryDsl(anyLong())).thenReturn(dto)
                .thenReturn(null);
        when(placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(anyLong(), anyLong())).thenReturn(execute);

        //when
        LeisureResponseDto leisureResponseOk = placeService.detailLeisure(1L, 1L);
        LeisureResponseDto leisureResponseEmpty = placeService.detailLeisure(1L, 1L);

        //then
        assertEquals(leisureResponseOk.getName(), dto.getName());
        assertNull(leisureResponseEmpty);
    }

    @Test
    @DisplayName("문화시설 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void detailCultureTest() throws Exception {
        //given
        CultureResponseDto dto = CultureResponseDto.builder().name("test").build();
        boolean execute = true;

        when(placeRepository.findCultureByPlaceIdQueryDsl(anyLong())).thenReturn(dto)
                .thenReturn(null);
        when(placeRepository.findPlaceLikeByPlaceIdUserSeqQueryDsl(anyLong(), anyLong())).thenReturn(execute);

        //when
        CultureResponseDto cultureResponseOk = placeService.detailCulture(1L, 1L);
        CultureResponseDto cultureResponseEmpty = placeService.detailCulture(1L, 1L);

        //then
        assertEquals(cultureResponseOk.getName(), dto.getName());
        assertNull(cultureResponseEmpty);
    }
}