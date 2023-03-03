package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String name; // 데이트 코스 이름

    @Column(name = "start_date")
    private String startDate; // 시작 날짜

    @Column(name = "end_date")
    private String endDate; // 끝 날짜

    @Column(name = "sub_category")
    private String subCategory; // 소분류

    // 데이트 코스 - 일정
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plan_id")
    @JsonIgnore
    private Plan plan;

    // 데이트 코스 - (데이트 코스 - 장소)
    @OneToMany(mappedBy = "course")
    private List<CoursePlace> coursePlaces = new ArrayList<>();

    // 데이트 코스 - (유저 - 데이트 코스)
    @OneToMany(mappedBy = "course")
    private List<UserCourse> userCourses = new ArrayList<>();
}
