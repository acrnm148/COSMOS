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
@Table(name = "reviewplace")
@Builder
@Data
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

    // 연관관계 메서드(리뷰)
    public void setReview(Review review) {
        this.review = review;
        review.getReviewPlaces().add(this);
    }

    // 연관관계 메서드(장소)
    public void setPlace(Place place) {
        this.place = place;
        place.getReviewPlaces().add(this);
    }

    // 생성 메서드
    public static ReviewPlace createReviewPlace (Review review, Place place) {
        ReviewPlace reviewPlace = new ReviewPlace();

        reviewPlace.setReview(review);
        reviewPlace.setPlace(place);

        return reviewPlace;
    }

}
