package com.cosmos.back.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ReviewCategoryModelTest {

    @Test
    public void createReviewCategoryTest() throws Exception{
        //given
        Review review = Review.builder().reviewCategories(new ArrayList<>()).build();
        String reviewCategoryCode = "좋아요";

        //when
        ReviewCategory reviewCategory = ReviewCategory.createReviewCategory(reviewCategoryCode, review);

        //then
        Assertions.assertThat(reviewCategory.getReview()).isEqualTo(review);
        Assertions.assertThat(reviewCategory.getReviewCategoryCode()).isEqualTo(reviewCategoryCode);

    }
}
