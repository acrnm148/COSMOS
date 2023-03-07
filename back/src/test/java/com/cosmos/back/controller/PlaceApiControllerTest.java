package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.service.PlaceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
class PlaceApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeService;

    @Test
    @DisplayName("PlaceApiController 관광정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 관광정보() throws Exception {

        TourResponseDto mockDto = TourResponseDto.builder().name("관광").build();

        // mocking
        when(placeService.detailTour(anyLong()))
                .thenReturn(mockDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/places/tours/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(response).contains("관광");
    }

    @Test
    @DisplayName("PlaceApiController 축제정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 축제정보() throws Exception {

        FestivalResponseDto mockDto = FestivalResponseDto.builder().name("축제").build();

        // mocking
        when(placeService.detailFestival(anyLong()))
                .thenReturn(mockDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/places/festivals/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("축제");
    }

    @Test
    @DisplayName("PlaceApiController 카페정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 카페정보() throws Exception {

        CafeResponseDto mockDto = CafeResponseDto.builder().name("카페").build();

        // mocking
        when(placeService.detailCafe(anyLong()))
                .thenReturn(mockDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/places/cafes/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("카페");
    }

    @Test
    @DisplayName("PlaceApiController 식당정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 식당정보() throws Exception {

        RestaurantResponseDto mockDto = RestaurantResponseDto.builder().name("식당").build();

        // mocking
        when(placeService.detailRestaurant(anyLong()))
                .thenReturn(mockDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/places/restaurants/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("식당");
    }

    @Test
    @DisplayName("PlaceApiController 숙소정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 숙소정보() throws Exception {

        AccommodationResponseDto mockDto = AccommodationResponseDto.builder().name("숙소").build();

        // mocking
        when(placeService.detailAccommodation(anyLong()))
                .thenReturn(mockDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/places/accommodations/1") // accomodations(url 잘못쓴 경우)로 쓰면 404 Error
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("숙소");
    }

    @Test
    @DisplayName("PlaceApiController 문화시설정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 문화시설정보() throws Exception {

        CulturesResponseDto mockDto = CulturesResponseDto.builder().name("문화시설").build();

        // mocking
        when(placeService.detailCulture(anyLong()))
                .thenReturn(mockDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/places/cultures/1") // accomodations(url 잘못쓴 경우)로 쓰면 404 Error
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("문화시설");
    }

    @Test
    @DisplayName("PlaceApiController 레져정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 레져정보() throws Exception {

        LeisuresResponseDto mockDto = LeisuresResponseDto.builder().name("레져").build();

        // mocking
        when(placeService.detailLeisure(anyLong()))
                .thenReturn(mockDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/places/leisures/1") // accomodations(url 잘못쓴 경우)로 쓰면 404 Error
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("레져");
    }

    @Test
    @DisplayName("PlaceApiController 쇼핑정보")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 쇼핑정보() throws Exception {

        ShoppingResponseDto mockDto = ShoppingResponseDto.builder().name("레져").build();

        // mocking
        when(placeService.detailShopping(anyLong()))
                .thenReturn(mockDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/places/shoppings/1") // accomodations(url 잘못쓴 경우)로 쓰면 404 Error
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("쇼핑");
    }
}