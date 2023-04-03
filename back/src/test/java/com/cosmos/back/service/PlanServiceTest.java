package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.*;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.CoursePlace;
import com.cosmos.back.model.Plan;
import com.cosmos.back.model.User;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.plan.PlanRepository;
import com.cosmos.back.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @SpyBean
    private CoursePlaceRepository coursePlaceRepository;

    @SpyBean
    private PlaceRepository placeRepository;

    @SpyBean
    private UserRepository userRepository;

    @Test
    @DisplayName("커플 일정 생성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void createPlanTest() throws Exception {
        //given
        List<Course> listCourses = new ArrayList<>();
        List<CourseIdAndDate> courseIdAndDates = new ArrayList<>();

        Course course = Course.builder()
                .id(1L)
                .date("2023-01-01")
                .build();
        listCourses.add(course);
        courseIdAndDates.add(new CourseIdAndDate(course.getId(), course.getDate()));

        Plan plan = Plan.builder()
                .coupleId(1L)
                .planName("testPlan")
                .startDate("20230302")
                .endDate("20230321")
                .courses(listCourses)
                .build();
        PlanDto planDto = PlanDto.builder().plan(plan).build();
        planDto.setCourseIdAndDateList(courseIdAndDates);

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
        // 기존에 저장된 Plan
        List<Course> courseList = new ArrayList<>();

        Course course = Course.builder().build();
        courseList.add(course);

        Plan plan = Plan.builder().id(1L).planName("planTest").endDate("20230331").startDate("20230329").coupleId(1L).courses(courseList).build();
        // 수정을 위해 입력되는 PlanDto 생성 과정
        List<Course> inputCourseList = new ArrayList<>();
        List<CourseIdAndDate> inputCourseIdAndDatesList = new ArrayList<>();
        Course inputCourse = Course.builder()
                .id(1L)
                .date("2023-01-01")
                .name("inputCourseTest").build();
        inputCourseList.add(inputCourse);
        inputCourseIdAndDatesList.add(new CourseIdAndDate(inputCourse.getId(), inputCourse.getDate()));

        Plan inputPlan = Plan.builder().id(1L).planName("inputPlanTest").endDate("20230431").startDate("20230429").coupleId(1L).courses(inputCourseList).build();

        PlanDto planDto = PlanDto.builder().plan(inputPlan).build();
        planDto.setCourseIdAndDateList(inputCourseIdAndDatesList);

        // 오류 발생 course

        when(planRepository.findByIdAndCoupleId(anyLong(), anyLong())).thenReturn(plan);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(inputCourse));
        when(planRepository.save(any(Plan.class))).thenReturn(inputPlan);

        //when
        Plan result = planService.updatePlan(planDto);

        //then
        assertEquals(result.getPlanName(), inputPlan.getPlanName());

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

    @Test
    @DisplayName("커플 일정 월별 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void getPlanListByMonthTest() throws Exception{
        //given
        String date="202303";
        List<PlanDto> resultList = new ArrayList<>();
        Plan plan = Plan.builder()
                .id(1L)
                .coupleId(123123L)
                .planName("cosmos plan")
                .build();
        PlanDto planDto = PlanDto.builder()
                .plan(plan)
                .build();
        resultList.add(planDto);

        List<PlanCourseDto> plansByMonth = new ArrayList<>();
        PlanCourseDto planCourseDto = PlanCourseDto.builder()
                .id(1L)
                .name("test course")
                .date("2023-03-01")
                .build();
        plansByMonth.add(planCourseDto);

        List<SimpleCourseDto> courses = new ArrayList<>();
        SimpleCourseDto simpleCourseDto = SimpleCourseDto.builder()
                .id(1L)
                .name("test course")
                .build();
        courses.add(simpleCourseDto);

        List<SimplePlaceDto> places = new ArrayList<>();
        SimplePlaceDto simplePlaceDto = SimplePlaceDto.builder()
                .courseId(1L)
                .name("장소명")
                .build();
        SimplePlaceDto simplePlaceDto2 = SimplePlaceDto.builder()
                .courseId(2L)
                .name("장소명")
                .build();
        SimplePlaceDto simplePlaceDto3 = SimplePlaceDto.builder()
                .courseId(3L)
                .name("장소명")
                .build();
        places.add(simplePlaceDto);
        places.add(simplePlaceDto2);
        places.add(simplePlaceDto3);

        when(planRepository.findByCoupleIdAndMonthQueryDsl(1L, "202304", "202303"))
                .thenReturn(plansByMonth);
        when(placeRepository.findSimplePlaceDtoByCourseIdQueryDsl(2L)).thenReturn(places);

        //when
        planService.getPlanListByMonth(1L, "202303");
        planService.getPlanListByMonth(1L, "202312");

        //then
        assertThat(resultList.get(0).getCoupleId()).isEqualTo(123123L);

    }

    @Test
    @DisplayName("커플 일정 일별 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void getPlanListByDayTest() throws Exception{
        //given
        String date="20230301";
        List<PlanDto> resultList = new ArrayList<>();
        Plan plan = Plan.builder()
                .id(1L)
                .coupleId(123123L)
                .planName("cosmos plan")
                .build();
        PlanDto planDto = PlanDto.builder()
                .plan(plan)
                .build();
        resultList.add(planDto);

        List<PlanCourseDto> plansByDay = new ArrayList<>();
        PlanCourseDto planCourseDto = PlanCourseDto.builder()
                .id(1L)
                .name("test course")
                .date("2023-03-01")
                .build();
        plansByDay.add(planCourseDto);

        List<SimpleCourseDto> courses = new ArrayList<>();
        SimpleCourseDto simpleCourseDto = SimpleCourseDto.builder()
                .id(1L)
                .name("test course")
                .build();
        courses.add(simpleCourseDto);

        List<SimplePlaceDto> places = new ArrayList<>();
        SimplePlaceDto simplePlaceDto = SimplePlaceDto.builder()
                .courseId(1L)
                .name("장소명")
                .build();
        SimplePlaceDto simplePlaceDto2 = SimplePlaceDto.builder()
                .courseId(2L)
                .name("장소명")
                .build();
        SimplePlaceDto simplePlaceDto3 = SimplePlaceDto.builder()
                .courseId(3L)
                .name("장소명")
                .build();
        places.add(simplePlaceDto);
        places.add(simplePlaceDto2);
        places.add(simplePlaceDto3);

        when(planRepository.findByCoupleIdAndMonthQueryDsl(1L, "202304", "202303"))
                .thenReturn(plansByMonth);
        when(placeRepository.findSimplePlaceDtoByCourseIdQueryDsl(2L)).thenReturn(places);

        //when
        planService.getPlanListByMonth(1L, "202303");
        planService.getPlanListByMonth(1L, "202312");

        //then
        assertThat(resultList.get(0).getCoupleId()).isEqualTo(123123L);

    }
}
