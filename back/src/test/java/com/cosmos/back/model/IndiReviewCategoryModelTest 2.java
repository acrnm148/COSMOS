package com.cosmos.back.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class IndiReviewCategoryModelTest {

    @Test
    public void createIndiReviewCategoryTest() throws Exception{
        //given
        Review review = Review.builder().indiReviewCategories(new ArrayList<>()).build();
        String indiReviewCategory = "ReviewCategory";

        //when
        IndiReviewCategory result = IndiReviewCategory.createIndiReviewCategory(indiReviewCategory, review);

        //then
        Assertions.assertThat(result.getReview()).isEqualTo(review);
        Assertions.assertThat(result.getReviewCategory()).isEqualTo(indiReviewCategory);

    }
}
