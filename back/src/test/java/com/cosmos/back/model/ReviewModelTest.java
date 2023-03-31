package com.cosmos.back.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewModelTest {
    @Mock
    private Review review;

    @Test
    public void createReviewTest() throws Exception {
        //given
        User user = User.builder().reviews(new ArrayList<>()).userName("userNameTest").build();
        String contents = "contentsTest";
        Integer score = 5;
        String formatedNow = "20230330";
        List<String> urls = new ArrayList<>();
        urls.add("urlTest1");
        urls.add("urlTest2");
        urls.add("urlTest3");
        String nickname = "nickNameTest";
        Boolean contentsOpen = true;
        Boolean imageOpen = true;

        //when
        Review result = Review.createReview(user, contents, score, formatedNow, urls, nickname, contentsOpen, imageOpen);

        //then
        assertThat(result.getContents()).isEqualTo(contents);
        assertEquals(result.getImg1(), urls.get(0));

    }
}
