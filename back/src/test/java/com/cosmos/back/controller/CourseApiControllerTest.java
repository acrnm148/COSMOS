package com.cosmos.back.controller;

import antlr.Token;
import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.dto.response.place.FestivalResponseDto;
import com.cosmos.back.service.CourseService;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
public class CourseApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    @MockBean
    private JwtService jwtService;

    @Test
    @DisplayName("CourseApiController 코스 생성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, courseResponseDto가 잘 나오는지 확인
    public void 코스생성() throws Exception {

        CourseResponseDto courseResponseDto = CourseResponseDto.builder().name("예시 코스").date("20230324").courseId(1L).orders(1).build();

        // mocking
        when(courseService.createCourse(any(CourseRequestDto.class))).thenReturn(courseResponseDto);

        Map<String, Object> courseRequestDto = new HashMap<>();
        courseRequestDto.put("sido", "서울특별시");
        courseRequestDto.put("gugun", "강남구");

        List<String> categories = new ArrayList<>();
        categories.add("cafe");
        categories.add("restaurant");
        categories.add("cafe");
        courseRequestDto.put("categories", categories);

        courseRequestDto.put("userSeq", 1L);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseRequestDto))
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("20230324");
    }

    @Test
    @DisplayName("CourseApiController 코스 찜")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, courseResponseDto가 잘 나오는지 확인
    public void 코스_찜() throws Exception {

        // mocking
        when(courseService.likeCourse(anyLong())).thenReturn(19950203L);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/courses/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).isEqualTo("19950203");
    }

    @Test
    @DisplayName("CourseApiController 코스 찜 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, courseResponseDto가 잘 나오는지 확인
    public void 코스_찜_삭제() throws Exception {

        // mocking
        when(courseService.deleteCourse(anyLong())).thenReturn(19950203L);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/courses/1")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).isEqualTo("19950203");
    }

    @Test
    @DisplayName("CourseApiController 내 찜한 코스 보기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, courseResponseDto가 잘 나오는지 확인
    public void 내_찜한_코스_보기() throws Exception {

        List<CourseResponseDto> mockList = new ArrayList<>();

        CourseResponseDto mockDto1 = CourseResponseDto.builder().name("mock dto1").build();
        CourseResponseDto mockDto2 = CourseResponseDto.builder().name("mock dto2").build();
        CourseResponseDto mockDto3 = CourseResponseDto.builder().name("mock dto3").build();

        mockList.add(mockDto1);
        mockList.add(mockDto2);
        mockList.add(mockDto3);

        // mocking
        when(courseService.listLikeCourse(anyLong())).thenReturn(mockList);
        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString())).thenReturn(JwtState.SUCCESS);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/courses/users/1")
                .header("Authorization", "Bearer token value")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("mock dto1");
        Assertions.assertThat(response).contains("mock dto2");
        Assertions.assertThat(response).contains("mock dto3");
    }
}
