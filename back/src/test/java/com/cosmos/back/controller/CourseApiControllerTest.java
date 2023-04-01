package com.cosmos.back.controller;

import antlr.Token;
import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.request.*;
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
import org.springframework.boot.test.mock.mockito.SpyBean;
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

    @SpyBean
    private JwtService jwtService;

    @Test
    @DisplayName("CourseApiController 코스 생성(추천 알고리즘)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, courseResponseDto가 잘 나오는지 확인
    public void 코스_생성_추천알고리즘() throws Exception {

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
    @DisplayName("CourseApiController 코스 생성(사용자 생성)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 코스_생성_사용자_생성() throws Exception {
        CouserUesrRequestDto mockCourseUserRequestDto = CouserUesrRequestDto.builder().name("mock name").build();

        when(courseService.createCourseByUser(anyLong(), any(CouserUesrRequestDto.class))).thenReturn(1995L);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/courses/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCourseUserRequestDto))
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("1995");
    }

    @Test
    @DisplayName("CourseApiController 코스 찜")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, courseResponseDto가 잘 나오는지 확인
    public void 코스_찜() throws Exception {
        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("courseId", "1995");
        mockMap.put("wish", "true");
        mockMap.put("name", "mock name");

        CourseNameRequestDto mockCourseNameRequestDto = CourseNameRequestDto.builder().name("mock name").build();

        // mocking
        when(courseService.likeCourse(anyLong(), anyString())).thenReturn(mockMap);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/courses/1")
                .content(objectMapper.writeValueAsString(mockCourseNameRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).isEqualTo("1995");
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

    @Test
    @DisplayName("CourseApiController 내 찜한 코스 보기 userSeq 실패")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, courseResponseDto가 잘 나오는지 확인
    public void 내_찜한_코스_보기_userSeq_fail() throws Exception {

        List<CourseResponseDto> mockList = new ArrayList<>();

        CourseResponseDto mockDto1 = CourseResponseDto.builder().name("mock dto1").build();
        CourseResponseDto mockDto2 = CourseResponseDto.builder().name("mock dto2").build();
        CourseResponseDto mockDto3 = CourseResponseDto.builder().name("mock dto3").build();

        mockList.add(mockDto1);
        mockList.add(mockDto2);
        mockList.add(mockDto3);

        // mocking
        when(courseService.listLikeCourse(anyLong())).thenReturn(mockList);
        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString())).thenReturn(JwtState.MISMATCH_USER);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/courses/users/1")
                .header("Authorization", "Bearer token value")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("401");
        Assertions.assertThat(response).contains("유저 불일치");
    }

    @Test
    @DisplayName("CourseApiController 내 찜한 코스 보기 access 만료")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // HTTP Status 200, courseResponseDto가 잘 나오는지 확인
    public void 내_찜한_코스_보기_aceess_만료() throws Exception {

        List<CourseResponseDto> mockList = new ArrayList<>();

        CourseResponseDto mockDto1 = CourseResponseDto.builder().name("mock dto1").build();
        CourseResponseDto mockDto2 = CourseResponseDto.builder().name("mock dto2").build();
        CourseResponseDto mockDto3 = CourseResponseDto.builder().name("mock dto3").build();

        mockList.add(mockDto1);
        mockList.add(mockDto2);
        mockList.add(mockDto3);

        // mocking
        when(courseService.listLikeCourse(anyLong())).thenReturn(mockList);
        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString())).thenReturn(JwtState.EXPIRED_ACCESS);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/courses/users/1")
                .header("Authorization", "Bearer token value")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("401");
        Assertions.assertThat(response).contains("accessToken 만료 또는 잘못된 값입니다.");
    }

    @Test
    @DisplayName("CourseApiController 내 코스 상세 보기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 내_코스_상세_보기() throws Exception {

        CourseResponseDto mockCourseResponseDto = CourseResponseDto.builder().date("19950203").build();

        // mocking
        when(courseService.getMyCourseDetail(anyLong())).thenReturn(mockCourseResponseDto);
        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString())).thenReturn(JwtState.SUCCESS);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/courses/1/users/1")
                .header("Authorization", "Bearer token value")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("19950203");
    }

    @Test
    @DisplayName("CourseApiController 내 코스 상세 보기 userSeq 실패")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 내_코스_상세_보기_userSeq_fail() throws Exception {

        CourseResponseDto mockCourseResponseDto = CourseResponseDto.builder().date("19950203").build();

        // mocking
        when(courseService.getMyCourseDetail(anyLong())).thenReturn(mockCourseResponseDto);
        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString())).thenReturn(JwtState.MISMATCH_USER);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/courses/1/users/1")
                .header("Authorization", "Bearer token value")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("401");
        Assertions.assertThat(response).contains("유저 불일치");
    }

    @Test
    @DisplayName("CourseApiController 내 코스 상세 보기 access 만료")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 내_코스_상세_보기_aceess_만료() throws Exception {

        CourseResponseDto mockCourseResponseDto = CourseResponseDto.builder().date("19950203").build();

        // mocking
        when(courseService.getMyCourseDetail(anyLong())).thenReturn(mockCourseResponseDto);
        when(jwtService.checkUserSeqWithAccess(anyLong(), anyString())).thenReturn(JwtState.EXPIRED_ACCESS);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/courses/1/users/1")
                .header("Authorization", "Bearer token value")
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("401");
        Assertions.assertThat(response).contains("accessToken 만료 또는 잘못된 값입니다.");
    }

    @Test
    @DisplayName("CourseApiController 코스내용 수정(이름 변경)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 코스_내용_수정_name_only() throws Exception {

        CourseUpdateContentsRequestDto mockCourseUpdateContentRequestDto = CourseUpdateContentsRequestDto.builder().name("mcok user").build();

        // mocking
        when(courseService.updateCourseContents(anyLong(), any(CourseUpdateContentsRequestDto.class))).thenReturn(1995L);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/courses/1/contents")
                .content(objectMapper.writeValueAsString(mockCourseUpdateContentRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("1995");
    }

    @Test
    @DisplayName("CourseApiController 코스내용 수정(장소 추가)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 코스_내용_수정_장소_추가() throws Exception {

        CourseUpdateAddDelRequestDto mockCourseUpdateAddDelRequestDto = CourseUpdateAddDelRequestDto.builder().placeId(1L).build();

        // mocking
        when(courseService.updateCourseAdd(anyLong(), any(CourseUpdateAddDelRequestDto.class))).thenReturn(1995L);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/courses/1/add")
                .content(objectMapper.writeValueAsString(mockCourseUpdateAddDelRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("1995");
    }

    @Test
    @DisplayName("CourseApiController 코스내용 수정(장소 삭제)")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 코스_내용_수정_장소_삭제() throws Exception {

        CourseUpdateAddDelRequestDto mockCourseUpdateAddDelRequestDto = CourseUpdateAddDelRequestDto.builder().placeId(1L).build();

        // mocking
        when(courseService.updateCourseDelete(anyLong(), any(CourseUpdateAddDelRequestDto.class))).thenReturn(1995L);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/courses/1/delete")
                .content(objectMapper.writeValueAsString(mockCourseUpdateAddDelRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("1995");
    }

    @Test
    @DisplayName("CourseApiController 코스 순서 수정")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 코스_순서_수정() throws Exception {

        CourseUpdateOrdersRequestDto mockCourseUpdateOrdersRequestDto = CourseUpdateOrdersRequestDto.builder().places(new ArrayList<>()).build();

        // mocking
        when(courseService.updateCourseOrders(anyLong(), any(CourseUpdateOrdersRequestDto.class))).thenReturn(1995L);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/courses/1/orders")
                .content(objectMapper.writeValueAsString(mockCourseUpdateOrdersRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().
                getResponse().
                getContentAsString();

        Assertions.assertThat(response).contains("1995");
    }
}
