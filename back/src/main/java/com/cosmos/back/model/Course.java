package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
@Data
@ToString(exclude = "plan")
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String name; // 데이트 코스 이름

    @Column(name = "date")
    private String date; // 코스 날짜

    @Column(name = "orders")
    private Integer orders; // 순서

    @Column(name = "wish")
    private Boolean wish; // 찜

    @Column(name = "couple_id")
    private Long coupleId; // 커플 아이디

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_seq")
    @JsonIgnore
    private User user; // 유저

    // 데이트 코스 - 일정
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plan_id") //외래키, 주인은 course
    @JsonIgnore
    private Plan plan;

    // 데이트 코스 - (데이트 코스 - 장소)
    //@JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<CoursePlace> coursePlaces = new ArrayList<>();

    // 연관관계 메서드 - (코스 - 유저)
    public void setUser(User user) {
        this.user = user;
        user.getCourses().add(this);
    }


    // 생성 메서드
    public static Course createCourse (User user) {
        Course course = new Course();

        course.setUser(user);

        course.setPlan(null);
        course.setName(null);
        course.setDate(null);
        course.setOrders(0);
        course.setWish(false);

        return course;
    }

    // 생성 메서드
    public static Course createCourseByUser (User user, String name) {
        Course course = new Course();

        course.setUser(user);

        course.setPlan(null);
        course.setName(name);
        course.setDate(null);
        course.setOrders(0);
        course.setWish(false);

        return course;
    }
}
