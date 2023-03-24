package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
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
}
