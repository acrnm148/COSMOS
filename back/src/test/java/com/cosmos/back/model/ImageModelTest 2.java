package com.cosmos.back.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageModelTest {

    @Test
    public void createImageTest() throws Exception{
        //given
        String imageUrl = "imageUrl";
        Review review = Review.builder().id(1L).reviewImages(new ArrayList<>()).build();
        Long coupleId = 1L;

        //when
        Image image = Image.createImage(imageUrl, coupleId, review);

        //then
        assertThat(image.getImageUrl()).isEqualTo(imageUrl);
        assertThat(image.getReview().getId()).isEqualTo(review.getId());

    }
}
