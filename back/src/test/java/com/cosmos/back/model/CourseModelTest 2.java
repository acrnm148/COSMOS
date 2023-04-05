package com.cosmos.back.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseModelTest {

    @Test
    public void createCourseTest() throws Exception{
        //given
        User user = User.builder().userSeq(1L).courses(new ArrayList<>()).userName("userName").build();

        //when
        Course course = Course.createCourse(user);

        //then
        assertThat(course.getUser().getUserSeq()).isEqualTo(user.getUserSeq());

    }

    @Test
    public void createCourseByUserTest() throws Exception{
        //given
        User user = User.builder().userSeq(1L).courses(new ArrayList<>()).userName("userName").build();
        String name = "courseName";

        //when
        Course course = Course.createCourseByUser(user, name);

        //then
        assertThat(course.getUser().getUserName()).isEqualTo(user.getUserName());
        assertThat(course.getName()).isEqualTo(name);

    }

}
