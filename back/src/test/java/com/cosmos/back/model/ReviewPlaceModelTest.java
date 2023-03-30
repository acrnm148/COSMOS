package com.cosmos.back.model;

import com.cosmos.back.model.place.Place;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ReviewPlaceModelTest {

    @Test
    public void createReviewPlaceTest() throws Exception{
        //given
        Review review = Review.builder().reviewPlaces(new ArrayList<>()).build();
        Place place = Place.builder().reviewPlaces(new ArrayList<>()).build();

        //when
        ReviewPlace reviewPlace = ReviewPlace.createReviewPlace(review, place);

        //then
        Assertions.assertThat(reviewPlace.getPlace()).isEqualTo(place);
        Assertions.assertThat(reviewPlace.getReview()).isEqualTo(review);

    }
}
