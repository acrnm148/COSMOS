package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.GugunDto;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.place.Gugun;
import com.cosmos.back.model.place.Sido;
import com.cosmos.back.service.PlaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
class PlaceApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlaceService placeService;

    @Test
    @DisplayName("시/도 정보 가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 Sido 데이터를 형식에 맞게 잘 가져오는지 확인
    public void SidoControllerTest() throws Exception {
        //given
        // sido에 관한 Mock 데이터 생성
        List<Sido> sidoResponseDto = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            sidoResponseDto.add(new Sido("code", "test"));
        }
        // listSido를 실행할 때 sidoMockList를 불러온다.
        when(placeService.listSido()).thenReturn(sidoResponseDto);

        //when
        // get으로 sido를 불러오는 요청을 보낸다.
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/sido"));

        //then
        //OK를 호출하는지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();

        // 받은 데이터를 sido List로 변환
        Sido[] sido = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), Sido[].class);
        List<Sido> sidoList = Arrays.asList(sido);

        // 해당 리스트의 개수가 5개인지 확인
        assertThat(sidoList.size()).isEqualTo(5);
        assertThat(sidoList.get(0).getSidoCode()).isEqualTo("code");
    }

    @Test
    @DisplayName("구/군 정보 가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 gugun 데이터를 형식에 맞게 잘 가져오는지 확인
    public void gugunControllerTest() throws Exception {
        //given
        // Sido Mock 데이터
        List<Sido> sidoList = new ArrayList<>();
        sidoList.add(new Sido("1", "Seoul"));

        // gugun Mock 데이터
        List<GugunDto> gugunDtoList = new ArrayList<>();
        gugunDtoList.add(new GugunDto("1", "GangNam"));
        gugunDtoList.add(new GugunDto("2", "GangBuk"));

        // stub
        when(placeService.listGugun(1)).thenReturn(gugunDtoList);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/gugun/1"));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();

        GugunDto[] gugunDto = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), GugunDto[].class);
        List<GugunDto> gugunDtoListData = Arrays.asList(gugunDto);

        assertThat(gugunDtoListData.size()).isEqualTo(2);
        assertThat(gugunDtoListData.get(0).getGugunCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("장소 상세 정보 가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // 내용이 있을 경우, Http Status 200, 해당 ResponseDto 반환 확인
    // 내용이 없을 경우, Http Status 204, null 반환 확인
    public void detailPlaceControllerTest() throws Exception {
        //given
        TourResponseDto tourResponseDto = TourResponseDto.builder().type("tour").build();
        FestivalResponseDto festivalResponseDto = FestivalResponseDto.builder().type("festival").startDate("20230326").build();
        AccommodationResponseDto accommodationResponseDto = AccommodationResponseDto.builder().type("accommodation").build();
        RestaurantResponseDto restaurantResponseDto = RestaurantResponseDto.builder().type("restaurant").build();
        CafeResponseDto cafeResponseDto = CafeResponseDto.builder().type("cafe").build();
        ShoppingResponseDto shoppingResponseDto = ShoppingResponseDto.builder().type("shopping").build();
        LeisureResponseDto leisureResponseDto = LeisureResponseDto.builder().type("leisure").build();
        CultureResponseDto cultureResponseDto = CultureResponseDto.builder().type("culture").build();


        //stub
        when(placeService.detailTour(anyLong(), anyLong())).thenReturn(tourResponseDto)
                .thenReturn(null);
        when(placeService.detailFestival(anyLong(), anyLong())).thenReturn(festivalResponseDto)
                .thenReturn(null);
        when(placeService.detailAccommodation(anyLong(), anyLong())).thenReturn(accommodationResponseDto)
                .thenReturn(null);
        when(placeService.detailRestaurant(anyLong(), anyLong())).thenReturn(restaurantResponseDto)
                .thenReturn(null);
        when(placeService.detailCafe(anyLong(), anyLong())).thenReturn(cafeResponseDto)
                .thenReturn(null);
        when(placeService.detailShopping(anyLong(), anyLong())).thenReturn(shoppingResponseDto)
                .thenReturn(null);
        when(placeService.detailLeisure(anyLong(), anyLong())).thenReturn(leisureResponseDto)
                .thenReturn(null);
        when(placeService.detailCulture(anyLong(), anyLong())).thenReturn(cultureResponseDto)
                .thenReturn(null);

        //1. when(내용이 모두 존재할 경우)
        ResultActions resultTourActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/tour"));
        ResultActions resultFestivalActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/festival"));
        ResultActions resultAccommodationActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/accommodation"));
        ResultActions resultRestaurantActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/restaurant"));
        ResultActions resultCafeActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/cafe"));
        ResultActions resultShoppingActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/shopping"));
        ResultActions resultLeisureActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/leisure"));
        ResultActions resultCultureActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/culture"));

        //1. then
        // tour verify
        MvcResult tourMvcResult = resultTourActions.andExpect(status().isOk()).andReturn();
        TourResponseDto tourResponse = new Gson().fromJson(tourMvcResult.getResponse().getContentAsString(), tourResponseDto.getClass());
        assertThat(tourResponse.getType()).isEqualTo("tour");

        // festival verify
        MvcResult festivalMvcResult = resultFestivalActions.andExpect(status().isOk()).andReturn();
        FestivalResponseDto festivalResponse = new Gson().fromJson(festivalMvcResult.getResponse().getContentAsString(), festivalResponseDto.getClass());
        assertThat(festivalResponse.getType()).isEqualTo("festival");

        //accommodation verify
        MvcResult accommodationMvcResult = resultAccommodationActions.andExpect(status().isOk()).andReturn();
        AccommodationResponseDto accommodationResponse = new Gson().fromJson(accommodationMvcResult.getResponse().getContentAsString(), accommodationResponseDto.getClass());
        assertThat(accommodationResponse.getType()).isEqualTo("accommodation");

        //restaurant verify
        MvcResult restaurantMvcResult = resultRestaurantActions.andExpect(status().isOk()).andReturn();
        RestaurantResponseDto restaurantResponse = new Gson().fromJson(restaurantMvcResult.getResponse().getContentAsString(), restaurantResponseDto.getClass());
        assertThat(restaurantResponse.getType()).isEqualTo("restaurant");

        //cafe verify
        MvcResult cafeMvcResult = resultCafeActions.andExpect(status().isOk()).andReturn();
        CafeResponseDto cafeResponse = new Gson().fromJson(cafeMvcResult.getResponse().getContentAsString(), cafeResponseDto.getClass());
        assertThat(cafeResponse.getType()).isEqualTo("cafe");

        //shopping verify
        MvcResult shoppingMvcResult = resultShoppingActions.andExpect(status().isOk()).andReturn();
        ShoppingResponseDto shoppingResponse = new Gson().fromJson(shoppingMvcResult.getResponse().getContentAsString(), shoppingResponseDto.getClass());
        assertThat(shoppingResponse.getType()).isEqualTo("shopping");

        //leisure verify
        MvcResult leisureMvcResult = resultLeisureActions.andExpect(status().isOk()).andReturn();
        LeisureResponseDto leisureResponse = new Gson().fromJson(leisureMvcResult.getResponse().getContentAsString(), leisureResponseDto.getClass());
        assertThat(leisureResponse.getType()).isEqualTo("leisure");

        //culture verify
        MvcResult cultureMvcResult = resultCultureActions.andExpect(status().isOk()).andReturn();
        CultureResponseDto cultureResponse = new Gson().fromJson(cultureMvcResult.getResponse().getContentAsString(), cultureResponseDto.getClass());
        assertThat(cultureResponse.getType()).isEqualTo("culture");


        //2. when(내용이 존재하지 않을 경우)
        ResultActions resultEmptyTourActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/tour"));
        ResultActions resultEmptyFestivalActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/festival"));
        ResultActions resultEmptyAccommodationActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/accommodation"));
        ResultActions resultEmptyRestaurantActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/restaurant"));
        ResultActions resultEmptyCafeActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/cafe"));
        ResultActions resultEmptyShoppingActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/shopping"));
        ResultActions resultEmptyLeisureActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/leisure"));
        ResultActions resultEmptyCultureActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/culture"));

        //then(null값 반환 및 204 상태 응답)
        // tour verify
        MvcResult tourEmptyMvcResult = resultEmptyTourActions.andExpect(status().isNoContent()).andReturn();
        assertThat(tourEmptyMvcResult.getResponse().getContentAsString()).isEmpty();

        // festival verify
        MvcResult festivalEmptyMvcResult = resultEmptyFestivalActions.andExpect(status().isNoContent()).andReturn();
        assertThat(festivalEmptyMvcResult.getResponse().getContentAsString()).isEmpty();

        // accommodation verify
        MvcResult accommodationEmptyMvcResult = resultEmptyAccommodationActions.andExpect(status().isNoContent()).andReturn();
        assertThat(accommodationEmptyMvcResult.getResponse().getContentAsString()).isEmpty();

        // restaurant verify
        MvcResult restaurantEmptyMvcResult = resultEmptyRestaurantActions.andExpect(status().isNoContent()).andReturn();
        assertThat(restaurantEmptyMvcResult.getResponse().getContentAsString()).isEmpty();

        // cafe verify
        MvcResult cafeEmptyMvcResult = resultEmptyCafeActions.andExpect(status().isNoContent()).andReturn();
        assertThat(cafeEmptyMvcResult.getResponse().getContentAsString()).isEmpty();

        // shopping verify
        MvcResult shoppingEmptyMvcResult = resultEmptyShoppingActions.andExpect(status().isNoContent()).andReturn();
        assertThat(shoppingEmptyMvcResult.getResponse().getContentAsString()).isEmpty();

        // leisure verify
        MvcResult leisureEmptyMvcResult = resultEmptyLeisureActions.andExpect(status().isNoContent()).andReturn();
        assertThat(leisureEmptyMvcResult.getResponse().getContentAsString()).isEmpty();

        // culture verify
        MvcResult cultureEmptyMvcResult = resultEmptyCultureActions.andExpect(status().isNoContent()).andReturn();
        assertThat(cultureEmptyMvcResult.getResponse().getContentAsString()).isEmpty();


        // get 요청 시 type이 위 영역에 속하지 않을 시 404에러 발생
        ResultActions resultEmptyPlaceActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/detail/users/1/placeId/1/type/32"));
        resultEmptyPlaceActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("검색어 자동완성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // 검색어가 있을 경우, Http Status 200, 해당 ResponseDto 반환 확인
    // 검색어가 없을 경우, Http Status 204, null 반환 확인
    public void searchControllerTest() throws Exception {
        //given
        List<AutoCompleteResponseDto> list = new ArrayList<>();
        list.add(AutoCompleteResponseDto.builder().placeId(1L).name("name1").address("address1").thumbNailUrl("url1").type("type1").build());
        list.add(AutoCompleteResponseDto.builder().placeId(2L).name("name2").address("address2").thumbNailUrl("url2").type("type2").build());

        when(placeService.autoCompleteSearchPlacesByName(anyString())).thenReturn(list);

        //when(검색어가 존재할 경우)
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/auto/test"));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        AutoCompleteResponseDto[] responseDto = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), AutoCompleteResponseDto[].class);
        List<AutoCompleteResponseDto> resultData = Arrays.asList(responseDto);
        assertThat(resultData.size()).isEqualTo(2);


        // when(검색어가 없을 경우)
        ResultActions resultEmptyActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/places/auto/"));

        // then
        MvcResult mvcEmptyResult = resultEmptyActions.andExpect(status().isNoContent()).andReturn();
        assertThat(mvcEmptyResult.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    @DisplayName("PlaceApiController 장소 찜")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 장소_찜() throws Exception {

        Map<String, Long> mockMap = new HashMap<>();

        mockMap.put("user", 1995L);
        mockMap.put("place", 1995L);

        // mocking
        when(placeService.likePlace(anyLong(), anyLong())).thenReturn(mockMap);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/places/1/users/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(response).isEqualTo("");
    }

    @Test
    @DisplayName("PlaceApiController 장소 찜 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 장소_찜삭제() throws Exception {
        // mocking
        when(placeService.deleteLikePlace(anyLong(), anyLong())).thenReturn(1L);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/places/1/users/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(response).isEqualTo("1");
    }

//    @Test
//    @DisplayName("시/도, 구/군, 검색어, 검색 필터으로 검색")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, map을 형식에 맞게 잘 받아오는지 테스트
//    public void searchPlaceControllerTest() throws Exception{
//        //given
//        PlaceFilterResponseDto dto = PlaceFilterResponseDto.builder().places(new ArrayList<>()).midLatitude(1.0).midLongitude(1.0).build();
//
//        when(placeService.searchPlacesBySidoGugunTextFilter(anyLong(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(dto);
//
//        //when
//        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/places/search/users/1/sido/sidoTest/gugun/gugunTest/text/textTest/filter/filterTest?limit=10&offset=0"))
//                .andExpect(status().isOk())
//                .andReturn();
//        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/places/search/users/1/sido/gugun/text/textTest/filter/filterTest?limit=10&offset=0"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        MvcResult mvcResult3 = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/places/search/users/1/sido/sidoTest/gugun/gugunTest/text/filter/?limit=10&offset=0"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        //then
//        PlaceFilterResponseDto resultData1 = new Gson().fromJson(mvcResult1.getResponse().getContentAsString(), PlaceFilterResponseDto.class);
//        assertThat(resultData1.getMidLatitude()).isEqualTo(1.0);
//
//        PlaceFilterResponseDto resultData2 = new Gson().fromJson(mvcResult2.getResponse().getContentAsString(), PlaceFilterResponseDto.class);
//        assertThat(resultData2.getMidLatitude()).isEqualTo(1.0);
//
//        PlaceFilterResponseDto resultData3 = new Gson().fromJson(mvcResult3.getResponse().getContentAsString(), PlaceFilterResponseDto.class);
//        assertThat(resultData3.getMidLatitude()).isEqualTo(1.0);
//    }


//
//    @Test
//    @DisplayName("PlaceApiController 이름으로 장소 검색")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 이름으로_장소_검색() throws Exception {
//        PlaceListResponseDto mockDto1 = PlaceListResponseDto.builder().name("mcokDto1").build();
//        PlaceListResponseDto mockDto2 = PlaceListResponseDto.builder().address("mock address").build();
//        PlaceListResponseDto mockDto3 = PlaceListResponseDto.builder().score(4.98).build();
//        PlaceListResponseDto mockDto4 = PlaceListResponseDto.builder().thumbNailUrl("mock thumbnail url").build();
//        PlaceListResponseDto mockDto5 = PlaceListResponseDto.builder().detail("mock detail").build();
//
//        List<PlaceListResponseDto> mockList = new ArrayList<>();
//        mockList.add(mockDto1);
//        mockList.add(mockDto2);
//        mockList.add(mockDto3);
//        mockList.add(mockDto4);
//        mockList.add(mockDto5);
//
//        when(placeService.searchPlacesByName(anyString(), anyInt(), anyInt())).thenReturn(mockList);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/장소?limit=15&offset=0")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Assertions.assertThat(response).contains("mcokDto1");
//        Assertions.assertThat(response).contains("mock address");
//        Assertions.assertThat(response).contains("4.98");
//        Assertions.assertThat(response).contains("mock thumbnail url");
//        Assertions.assertThat(response).contains("mock detail");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 시도 가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 시도_가져오기() throws Exception {
//        Sido sido1 = Sido.builder().sidoName("서울특별시").sidoCode("11").build();
//        Sido sido2 = Sido.builder().sidoName("부산광역시").sidoCode("26").build();
//
//        List<Sido> mockList = new ArrayList<>();
//
//        mockList.add(sido1);
//        mockList.add(sido2);
//
//        when(placeService.listSido()).thenReturn(mockList);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/sido")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Assertions.assertThat(response).contains("서울특별시");
//        Assertions.assertThat(response).contains("부산광역시");
//        Assertions.assertThat(response).contains("11");
//        Assertions.assertThat(response).contains("26");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 구군 가져오기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 구군_가져오기() throws Exception {
//        Gugun gugun1 = Gugun.builder().gugunName("종로구").gugunCode("11110").build();
//        Gugun gugun2 = Gugun.builder().gugunName("동대문구").gugunCode("11230").build();
//
//        List<Gugun> mockList = new ArrayList<>();
//
//        mockList.add(gugun1);
//        mockList.add(gugun2);
//
//        List<GugunDto> mockDtoList = new ArrayList<>();
//        mockDtoList.add(new GugunDto(gugun1.getGugunName(), gugun1.getGugunCode()));
//
//        when(placeService.listGugun(anyString())).thenReturn(mockDtoList);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/gugun/11")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Assertions.assertThat(response).contains("종로구");
//        Assertions.assertThat(response).contains("11110");
//        Assertions.assertThat(response).contains("동대문구");
//        Assertions.assertThat(response).contains("11230");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 시도 구군으로 장소 검색")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 시도_구군으로_장소_검색() throws Exception {
//        PlaceListResponseDto place1 = PlaceListResponseDto.builder().name("장소1995L").build();
//        PlaceListResponseDto place2 = PlaceListResponseDto.builder().detail("장소 상세 1995").build();
//        PlaceListResponseDto place3 = PlaceListResponseDto.builder().address("장소 주소 1995").build();
//        PlaceListResponseDto place4 = PlaceListResponseDto.builder().thumbNailUrl("장소 썸네일 1995").build();
//        PlaceListResponseDto place5 = PlaceListResponseDto.builder().score(1995.02).build();
//
//        List<PlaceListResponseDto> mockList = new ArrayList<>();
//
//        mockList.add(place1);
//        mockList.add(place2);
//        mockList.add(place3);
//        mockList.add(place4);
//        mockList.add(place5);
//
//        when(placeService.searchPlacesBySidoGugun(anyString(), anyString(), anyInt(), anyInt())).thenReturn(mockList);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/search/sido/11/gugun/11230?limit=15&offset=0")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Assertions.assertThat(response).contains("장소1995L");
//        Assertions.assertThat(response).contains("장소 상세 1995");
//        Assertions.assertThat(response).contains("장소 주소 1995");
//        Assertions.assertThat(response).contains("장소 썸네일 1995");
//        Assertions.assertThat(response).contains("1995.02");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 검색어 자동완성")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void 검색어_자동완성() throws Exception {
//        AutoCompleteResponseDto dto1 = AutoCompleteResponseDto.builder().name("장시우 생일은 1995.02.03").build();
//        AutoCompleteResponseDto dto2 = AutoCompleteResponseDto.builder().name("반유진 생일은 1999.03.23").build();
//
//        List<AutoCompleteResponseDto> mockList = new ArrayList<>();
//
//        mockList.add(dto1);
//        mockList.add(dto2);
//
//        when(placeService.autoCompleteSearchPlacesByName(anyString())).thenReturn(mockList);
//
//        Map<String, String> autoCompleteRequestDto = new HashMap<>();
//        autoCompleteRequestDto.put("searchWord", "장시우 생일은 1995.02.03");
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/api/places/auto")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(autoCompleteRequestDto))
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Assertions.assertThat(response).contains("장시우 생일은 1995.02.03");
//        Assertions.assertThat(response).contains("반유진 생일은 1999.03.23");
//    }
}