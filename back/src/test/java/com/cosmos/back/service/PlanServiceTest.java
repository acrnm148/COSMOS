package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.PlanDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.Plan;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.plan.PlanRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
@Transactional
public class PlanServiceTest {

    @MockBean
    private PlanRepository planRepository;

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private PlanService planService;

    @Test
    @DisplayName("커플 일정 생성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void createPlanTest() throws Exception {
        //given
        List<Course> listCourses = new ArrayList<>();
        List<Long> listCourseIds = new ArrayList<>();

        Course course = Course.builder().id(1L).build();
        listCourses.add(course);
        listCourseIds.add(course.getId());

        Plan plan = Plan.builder()
                .coupleId(1L)
                .planName("testPlan")
                .startDate("20230302")
                .endDate("20230321")
                .courses(listCourses)
                .build();
        PlanDto planDto = PlanDto.builder().plan(plan).build();
        planDto.setCourseIds(listCourseIds);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(planRepository.save(any(Plan.class))).thenReturn(plan);

        //when
        Plan result = planService.createPlan(planDto);

        //then
        assertThat(result.getPlanName()).isEqualTo(plan.getPlanName());
    }

    @Test
    @DisplayName("커플 일정 상세보기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void getPlanDetailTest() throws Exception {
        //given
        Plan plan = Plan.builder().planName("planTest").build();

        when(planRepository.findByIdAndCoupleId(anyLong(), anyLong())).thenReturn(plan);

        //when
        Plan result = planService.getPlanDetail(1L, 1L);

        //then
        assertThat(result.getPlanName()).isEqualTo(plan.getPlanName());
    }

    @Test
    @DisplayName("커플 일정 수정")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void updatePlanTest() throws Exception{
        //given


        //when


        //then



    }

    @Test
    @DisplayName("커플 일정 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void deletePlanTest() throws Exception{
        //given
        Plan plan = Plan.builder().planName("planTest").build();

        when(planRepository.findByIdAndCoupleId(anyLong(), anyLong())).thenReturn(plan);
        doNothing().when(planRepository).delete(any(Plan.class));

        //when
        planService.deletePlan(1L, 1L);

        //then
        verify(planRepository, times(1)).delete(any(Plan.class));
        verify(planRepository, times(1)).findByIdAndCoupleId(anyLong(), anyLong());


    }

}
