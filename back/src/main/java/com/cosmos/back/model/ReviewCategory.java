package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviewcategory")
public class ReviewCategory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_category_id")
    private Long id;

    @Column(name = "review_category_code")
    private String reviewCategoryCode; // 리뷰 카테고리 상태 코드

    // 리뷰 상태 - 리뷰
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    @JsonIgnore
    private Review review;
}
