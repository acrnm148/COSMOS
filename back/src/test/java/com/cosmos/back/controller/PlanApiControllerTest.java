package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.PlanDto;
import com.cosmos.back.model.Plan;
import com.cosmos.back.service.PlanService;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
public class PlanApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanService planService;

    @Test
    @DisplayName("커플 일정 생성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void createPlanTest() throws Exception {
        //given
//        Plan plan = Plan.builder().planName("planTest").build();
//
//        when(planService.createPlan(any(PlanDto.class))).thenReturn(plan);
//
//        //when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/plans"));
//
//        //then
//        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
//        Plan result = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), Plan.class);
//        assertThat(result.getPlanName()).isEqualTo(plan.getPlanName());
    }

    @Test
    @DisplayName("커플 일정 상세보기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void getPlanDetailTest() throws Exception{

    }

}
