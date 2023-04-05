//package com.cosmos.back.controller;
//
//import com.cosmos.back.annotation.EnableMockMvc;
//import com.cosmos.back.dto.PlanDto;
//import com.cosmos.back.model.Plan;
//import com.cosmos.back.service.PlanService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@EnableMockMvc
//public class PlanApiControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private PlanService planService;
//
//    @Test
//    @DisplayName("커플 일정 생성")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void createPlanTest() throws Exception {
//        //given
//        Plan plan = Plan.builder().planName("planTest").build();
//        PlanDto planDto = PlanDto.builder().plan(plan).build();
//
//        when(planService.createPlan(any(PlanDto.class))).thenReturn(plan);
//
//        String content = objectMapper.writeValueAsString(planDto);
//
//        //when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/plans")
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        //then
//        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
//        Plan result = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), Plan.class);
//        assertThat(result.getPlanName()).isEqualTo(plan.getPlanName());
//    }
//
//    @Test
//    @DisplayName("커플 일정 상세보기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void getPlanDetailTest() throws Exception{
//        //given
//        Plan plan = Plan.builder().planName("planTest").build();
//
//        when(planService.getPlanDetail(anyLong(), anyLong())).thenReturn(plan);
//
//        //when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/plans/1/1"));
//
//        //then
//        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
//        Plan result = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), Plan.class);
//        assertThat(result.getPlanName()).isEqualTo(plan.getPlanName());
//    }
//
//    @Test
//    @DisplayName("커플 일정 수정")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void updatePlanTest() throws Exception{
//        //given
//        Plan plan = Plan.builder().planName("planTest").build();
//        PlanDto planDto = PlanDto.builder().plan(plan).build();
//        String content = objectMapper.writeValueAsString(planDto);
//
//        when(planService.updatePlan(any(PlanDto.class))).thenReturn(plan);
//
//        //when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/plans")
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        //then
//        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
//        Plan result = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), Plan.class);
//
//        assertThat(result.getPlanName()).isEqualTo(plan.getPlanName());
//    }
//
//    @Test
//    @DisplayName("커플 일정 삭제")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void deletePlanTest() throws Exception{
//        //given
//        doNothing().when(planService).deletePlan(anyLong(), anyLong());
//
//        //when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/plans/1/1"));
//
//        //then
//        resultActions.andExpect(status().isOk());
//        verify(planService, times(1)).deletePlan(1L, 1L);
//    }
//
//    @Test
//    @DisplayName("커플 월별 조회")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void getPlanListByMonthTest() throws Exception{
//        //given
//        List<PlanDto> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Plan plan = Plan.builder().planName("test" + Integer.toString(i)).build();
//            PlanDto planDto = PlanDto.builder().plan(plan).build();
//            list.add(planDto);
//        }
//        when(planService.getPlanListByMonth(anyLong(), anyString())).thenReturn(list);
//
//        //when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/plans/1/month/202303"));
//
//        //then
//        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
//        PlanDto[] dto = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), PlanDto[].class);
//        List<PlanDto> result = Arrays.asList(dto);
//
//        assertEquals(result.size(), 5);
//        assertThat(result.get(0).getPlanName()).isEqualTo(list.get(0).getPlanName());
//
//    }
//
//    @Test
//    @DisplayName("커플 일별 조회")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void getPlanListByDayTest() throws Exception{
//        //given
//        Plan plan = Plan.builder().planName("planTest").build();
//        PlanDto planDto = PlanDto.builder().plan(plan).build();
//
//        when(planService.getPlanListByDay(anyLong(), anyString())).thenReturn(planDto);
//
//        //when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/plans/1/day/20230329"));
//
//        //then
//        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
//        PlanDto result = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), PlanDto.class);
//        assertEquals(result.getPlanName(), plan.getPlanName());
//
//    }
//
//}
