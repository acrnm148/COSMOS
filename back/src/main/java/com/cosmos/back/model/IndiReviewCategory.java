package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "indireviewcategory")
@Builder
@Getter
@Setter
public class IndiReviewCategory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "indi_review_category_id")
    private Long id; // ID

    @Column(name = "indi_review_category")
    private String reviewCategory; // 리뷰 카테고리(개별)

    // 리뷰 상태 - 리뷰
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    @JsonIgnore
    private Review review;

    // 연관관계 메서드
    public void setReview(Review review) {
        this.review = review;
        review.getIndiReviewCategories().add(this);
    }

    // 생성 메서드
    public static IndiReviewCategory createIndiReviewCategory (String indiReviewCategory, Review review) {
        IndiReviewCategory reviewCategory = new IndiReviewCategory();

        reviewCategory.setReview(review);
        reviewCategory.setReviewCategory(indiReviewCategory);

        return reviewCategory;
    }
}
