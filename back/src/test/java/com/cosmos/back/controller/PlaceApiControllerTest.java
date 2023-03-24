package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.GugunDto;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.place.Gugun;
import com.cosmos.back.model.place.Sido;
import com.cosmos.back.service.PlaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
class PlaceApiControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private PlaceService placeService;
//
//    @Test
//    @DisplayName("PlaceApiController 관광정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 관광정보() throws Exception {
//
//        TourResponseDto mockDto = TourResponseDto.builder().name("관광").build();
//
//        // mocking
//        when(placeService.detailTour(anyLong()))
//                .thenReturn(mockDto);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/tours/1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Assertions.assertThat(response).contains("관광");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 축제정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 축제정보() throws Exception {
//
//        FestivalResponseDto mockDto = FestivalResponseDto.builder().name("축제").build();
//
//        // mocking
//        when(placeService.detailFestival(anyLong()))
//                .thenReturn(mockDto);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/festivals/1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn().
//                getResponse().
//                getContentAsString();
//
//        Assertions.assertThat(response).contains("축제");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 카페정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 카페정보() throws Exception {
//
//        CafeResponseDto mockDto = CafeResponseDto.builder().name("카페").build();
//
//        // mocking
//        when(placeService.detailCafe(anyLong()))
//                .thenReturn(mockDto);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/cafes/1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn().
//                getResponse().
//                getContentAsString();
//
//        Assertions.assertThat(response).contains("카페");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 식당정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 식당정보() throws Exception {
//
//        RestaurantResponseDto mockDto = RestaurantResponseDto.builder().name("식당").build();
//
//        // mocking
//        when(placeService.detailRestaurant(anyLong()))
//                .thenReturn(mockDto);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/restaurants/1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn().
//                getResponse().
//                getContentAsString();
//
//        Assertions.assertThat(response).contains("식당");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 숙소정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 숙소정보() throws Exception {
//
//        AccommodationResponseDto mockDto = AccommodationResponseDto.builder().name("숙소").build();
//
//        // mocking
//        when(placeService.detailAccommodation(anyLong()))
//                .thenReturn(mockDto);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/accommodations/1") // accomodations(url 잘못쓴 경우)로 쓰면 404 Error
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn().
//                getResponse().
//                getContentAsString();
//
//        Assertions.assertThat(response).contains("숙소");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 문화시설정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 문화시설정보() throws Exception {
//
//        CultureResponseDto mockDto = CultureResponseDto.builder().name("문화시설").build();
//
//        // mocking
//        when(placeService.detailCulture(anyLong()))
//                .thenReturn(mockDto);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/cultures/1") // accomodations(url 잘못쓴 경우)로 쓰면 404 Error
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn().
//                getResponse().
//                getContentAsString();
//
//        Assertions.assertThat(response).contains("문화시설");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 레져정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 레져정보() throws Exception {
//
//        LeisureResponseDto mockDto = LeisureResponseDto.builder().name("레져").build();
//
//        // mocking
//        when(placeService.detailLeisure(anyLong()))
//                .thenReturn(mockDto);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/leisures/1") // accomodations(url 잘못쓴 경우)로 쓰면 404 Error
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn().
//                getResponse().
//                getContentAsString();
//
//        Assertions.assertThat(response).contains("레져");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 쇼핑정보")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 쇼핑정보() throws Exception {
//
//        ShoppingResponseDto mockDto = ShoppingResponseDto.builder().name("쇼핑").build();
//
//        // mocking
//        when(placeService.detailShopping(anyLong()))
//                .thenReturn(mockDto);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/shoppings/1") // accomodations(url 잘못쓴 경우)로 쓰면 404 Error
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Assertions.assertThat(response).contains("쇼핑");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 장소 찜")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 장소_찜() throws Exception {
//        // mocking
//        when(placeService.likePlace(anyLong(), anyLong())).thenReturn(1995L);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/places/1/users/1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Assertions.assertThat(response).isEqualTo("1995");
//    }
//
//    @Test
//    @DisplayName("PlaceApiController 장소 찜 삭제")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
//    public void 장소_찜삭제() throws Exception {
//        // mocking
//        when(placeService.deleteLikePlace(anyLong(), anyLong())).thenReturn(1995L);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .delete("/api/places/1/users/1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        String response = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Assertions.assertThat(response).isEqualTo("1995");
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