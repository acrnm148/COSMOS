package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usercourse")
@Builder
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
}
