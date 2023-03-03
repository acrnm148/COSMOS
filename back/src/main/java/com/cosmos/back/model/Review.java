package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String contents; // 내용

    private Integer score; // 별점

    // 리뷰 - 유저
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_seq")
    @JsonIgnore
    private User user;

    // 리뷰 - (리뷰 - 장소)
    @OneToMany(mappedBy = "review")
    private List<ReviewPlace> reviewPlaces = new ArrayList<>();

    // 리뷰 - 리뷰 상태
    @OneToMany(mappedBy = "review")
    private List<ReviewCategory> reviewCategories = new ArrayList<>();
}
