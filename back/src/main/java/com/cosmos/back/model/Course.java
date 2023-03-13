package com.cosmos.back.model;

import com.cosmos.back.model.place.Place;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

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

    @Column(name = "sub_category")
    private String subCategory; // 소분류

    @Column(name = "orders")
    private Integer orders; // 순서

    // 데이트 코스 - 일정
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plan_id") //외래키, 주인은 course
    private Plan plan;

    // 데이트 코스 - (데이트 코스 - 장소)
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<CoursePlace> coursePlaces = new ArrayList<>();

    // 데이트 코스 - (유저 - 데이트 코스)
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<UserCourse> userCourses = new ArrayList<>();

    // 생성 메서드
    public static Course createCourse (String name, String date, String subCategory, Integer orders) {
        Course course = new Course();

        course.setPlan(null);
        course.setName(name);
        course.setDate(date);
        course.setSubCategory(subCategory);
        course.setOrders(orders);

        return course;
    }
}
