package com.cosmos.back.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "plan")
@Builder
public class Plan {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @Column(name = "main_category")
    private String mainCategory; // 대분류(일정 이름)

    @Column(name = "start_date")
    private String startDate; // 시작 날짜

    @Column(name = "end_date")
    private String endDate; // 끝 날짜

    @Column(name = "couple_id")
    private Long coupleId; // 커플 ID

    // 일정 - 데이트 코스
    @OneToMany(mappedBy = "plan")
    List<Course> courses;
}
