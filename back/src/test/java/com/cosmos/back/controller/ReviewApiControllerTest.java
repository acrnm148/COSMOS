package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.dto.response.review.ReviewResponseDto;
import com.cosmos.back.dto.response.review.ReviewUserResponseDto;
import com.cosmos.back.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
class ReviewApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("ReviewApiController 리뷰 생성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 리뷰_생성() throws Exception {

        String reviewRequestDto = objectMapper.writeValueAsString(ReviewRequestDto.builder().placeId(1995L).build());

        MockMultipartFile mockReviewRequestDto = new MockMultipartFile("dto", "dto", "application/json", reviewRequestDto.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test file".getBytes(StandardCharsets.UTF_8) );

        when(reviewService.createReview(any(ReviewRequestDto.class), anyLong(), any())).thenReturn(1995L);

        mockMvc.perform(multipart("/api/reviews/users/1")
                        .file(mockReviewRequestDto)
                        .file(file)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("ReviewApiController 리뷰 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 리뷰_삭제() throws Exception {

        when(reviewService.deleteReview(anyLong(), anyLong())).thenReturn(1995L);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/reviews/1/users/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("1995");
    }

    @Test
    @DisplayName("ReviewApiController 장소별로 유저, 커플 유저의 리뷰 모두 불러오기(커플 아이디가 있는 경우")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 장소별로_유저_커플_유저_리뷰_모두_불러오기_커플아이디_있음() throws Exception {

        ReviewResponseDto dto1 = ReviewResponseDto.builder().score(5).build();
        ReviewResponseDto dto2 = ReviewResponseDto.builder().score(4).build();

        List<ReviewResponseDto> mockReviewResponseDtoList = new ArrayList<>();
        mockReviewResponseDtoList.add(dto1);
        mockReviewResponseDtoList.add(dto2);

        when(reviewService.findReviewsInPlaceUserCouple(anyLong(), anyLong(), anyLong())).thenReturn(mockReviewResponseDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/reviews/users/1/coupleId/1/places/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("5");
        Assertions.assertThat(response).contains("4");
    }

    @Test
    @DisplayName("ReviewApiController 장소별로 유저, 커플 유저의 리뷰 모두 불러오기(커플 아이디가 없는 경우")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 장소별로_유저_커플_유저_리뷰_모두_불러오기_커플아이디_없음() throws Exception {

        ReviewResponseDto dto1 = ReviewResponseDto.builder().score(5).build();
        ReviewResponseDto dto2 = ReviewResponseDto.builder().score(4).build();

        List<ReviewResponseDto> mockReviewResponseDtoList = new ArrayList<>();
        mockReviewResponseDtoList.add(dto1);
        mockReviewResponseDtoList.add(dto2);

        when(reviewService.findReviewsInPlaceUserCouple(anyLong(), any(), anyLong())).thenReturn(mockReviewResponseDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/reviews/users/1/coupleId/places/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("5");
        Assertions.assertThat(response).contains("4");
    }

    @Test
    @DisplayName("ReviewApiController 장소별 리뷰 모두 불러오기(리뷰가 있는 경우)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 장소별_리뷰_모두_불러오기_리뷰_있음() throws Exception {

        ReviewResponseDto dto1 = ReviewResponseDto.builder().score(5).build();
        ReviewResponseDto dto2 = ReviewResponseDto.builder().score(4).build();

        List<ReviewResponseDto> mockReviewResponseDtoList = new ArrayList<>();
        mockReviewResponseDtoList.add(dto1);
        mockReviewResponseDtoList.add(dto2);

        when(reviewService.findReviewsInPlace(anyLong())).thenReturn(mockReviewResponseDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/reviews/places/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("5");
        Assertions.assertThat(response).contains("4");
    }

    @Test
    @DisplayName("ReviewApiController 장소별 리뷰 모두 불러오기(리뷰가 없는 경우)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 204, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 장소별_리뷰_모두_불러오기_리뷰_없음() throws Exception {
        List<ReviewResponseDto> mockReviewResponseDtoList = new ArrayList<>();

        when(reviewService.findReviewsInPlace(anyLong())).thenReturn(mockReviewResponseDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/reviews/places/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn().
                getResponse().
                getContentAsString();
    }

    @Test
    @DisplayName("ReviewApiController 내 리뷰 모두 불러오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 내_리뷰_모두_불러오기() throws Exception {
        List<ReviewUserResponseDto> mockReviewUserResponseDto = new ArrayList<>();

        ReviewUserResponseDto reviewUserResponseDto1 = ReviewUserResponseDto.builder().score(5).build();
        ReviewUserResponseDto reviewUserResponseDto2 = ReviewUserResponseDto.builder().score(4).build();

        mockReviewUserResponseDto.add(reviewUserResponseDto1);
        mockReviewUserResponseDto.add(reviewUserResponseDto2);

        when(reviewService.findReviewsInUser(anyLong())).thenReturn(mockReviewUserResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/reviews/users/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("5");
        Assertions.assertThat(response).contains("4");
    }

    @Test
    @DisplayName("ReviewApiController 리뷰 수정")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 리뷰_수정() throws Exception {
        ReviewRequestDto mockReviewRequestDto = ReviewRequestDto.builder().build();

        when(reviewService.changeReview(anyLong(), any(ReviewRequestDto.class), anyLong())).thenReturn(1995L);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/reviews/1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockReviewRequestDto))
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("1995");
    }

    @Test
    @DisplayName("ReviewApiController 리뷰 닉네임 생성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, Service에서 ResponseDto를 형식에 맞게 잘 받아오는지 테스트
    public void 리뷰_닉네임_생성() throws Exception {
        when(reviewService.findReviewNickName()).thenReturn("mock user nickname");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/reviews/nickname")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("mock user nickname");
    }
}