package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cosmos.back.model.place.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviewplace")
@Builder
public class ReviewPlace {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_place_id")
    private Long id;

    // (리뷰 - 장소) - 리뷰
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    @JsonIgnore
    private Review review;

    // (리뷰 - 장소) - 장소
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Place place;
}
