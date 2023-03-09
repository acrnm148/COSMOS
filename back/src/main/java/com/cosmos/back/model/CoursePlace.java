package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cosmos.back.model.place.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courseplace")
@Builder
@Data
public class CoursePlace {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "course_place_id")
    private Long id;

    private Integer orders; // 순서

    // (데이트 코스 - 장소) - 데이트 코스
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    // (데이트 코스 - 장소) - 장소
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Place place;

    // 연관관계 메서드
//    public void setPlace(User user) {
//        this.user = user;
//        user.getReviews().add(this);
//    }

    // 생성 메서드
    public static CoursePlace createCoursePlace (Course course, Place place) {
        CoursePlace coursePlace = new CoursePlace();



        return coursePlace;
    }
}
