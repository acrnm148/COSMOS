package com.cosmos.back.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewModelTest {



    @Test
    public void createReviewTest() throws Exception{
        //given
        User user = User.builder().userSeq(1L).reviews(new ArrayList<>()).build();
        String contents = "contents";
        Integer score = 5;
        String formatedNow = "20230403";
        String nickName = "nickName";
        Boolean contentsOpen = true;
        Boolean imageOpen = true;

        //when
        Review review = Review.createReview(user, contents, score, formatedNow, nickName, contentsOpen, imageOpen);

        //then
        assertEquals(review.getContents(), contents);
        assertEquals(review.getNickname(), nickName);
        assertEquals(review.getContentsOpen(), contentsOpen);


    }

}
