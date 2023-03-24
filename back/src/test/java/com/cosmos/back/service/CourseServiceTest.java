package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.CoursePlace;
import com.cosmos.back.model.User;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.course.CoursePlaceRepository;
import com.cosmos.back.repository.course.CourseRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @SpyBean
    private UserRepository userRepository;

    @Test
    @DisplayName("CourseService saveCourse 메소드")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void saveCourse() throws Exception {
        User user = User.builder().userName("테스트 유저가 될지도?").build();
        userRepository.save(user);

        User mockUser = User.builder().userSeq(1L).courses(new ArrayList<>()).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockUser));

        Course course = courseService.saveCourse(1L);

        Course foundCourse = courseRepository.findById(course.getId()).orElseThrow(() -> new IllegalArgumentException("no such data"));

        assertEquals(course.getId(), foundCourse.getId());
    }
}
