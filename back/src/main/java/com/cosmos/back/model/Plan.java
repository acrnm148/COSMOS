package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
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

    @Column(name = "create_time")
    private LocalDateTime createTime; // 생성일자

    @Column(name = "update_time")
    private LocalDateTime updateTime; // 수정일자

    // 일정 - 데이트 코스
//    @JsonIgnoreProperties({"plan"})
//    @OneToMany(mappedBy = "plan")
//    List<Course> courses;
    //직렬화(serialize)해서 임시로 메모리에 올려두는 작업
}
