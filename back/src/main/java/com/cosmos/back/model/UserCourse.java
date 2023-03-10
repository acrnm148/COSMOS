package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usercourse")
@Builder
@Data
public class UserCourse {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_course_id")
    private Long id;

    // 유저
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_seq")
    @JsonIgnore
    private User user;

    // 유저 - 데이트 코스
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;


    // 연관관계 메서드 - (유저 - 유저데이트코스)
    public void setUser(User user) {
        this.user = user;
        user.getUserCourses().add(this);
    }

    // 연관관계 메서드 - (데이트코스 - 유저데이트코스)
    public void setCourse(Course course) {
        this.course = course;
        course.getUserCourses().add(this);
    }

    // 생성 메서드
    public static UserCourse createUserCourse (User user, Course course) {
        UserCourse userCourse = new UserCourse();

        userCourse.setUser(user);
        userCourse.setCourse(course);

        return userCourse;
    }
}
