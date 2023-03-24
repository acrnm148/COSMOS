package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cosmos.back.model.place.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courseplace")
@Builder
@Getter
@Setter
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

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "user_seq")
//    @JsonIgnore
//    private User user;


    // 연관관계 메서드 - (데이트코스 - 데이트코스_장소)
    public void setCourse(Course course) {
        this.course = course;
        course.getCoursePlaces().add(this);
    }


    // 연관관계 메서드 - (장소 - 데이트코스_장소)
    public void setPlace(Place place) {
        this.place = place;
        place.getCoursePlaces().add(this);
    }

    // 생성 메서드
    public static CoursePlace createCoursePlace (Course course, Place place, Integer orders) {
        CoursePlace coursePlace = new CoursePlace();

        coursePlace.setCourse(course);
        coursePlace.setPlace(place);
        coursePlace.setOrders(orders);

        return coursePlace;
    }
}
