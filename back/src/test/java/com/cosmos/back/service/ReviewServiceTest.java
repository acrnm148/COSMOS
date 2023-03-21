package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.model.Review;
import com.cosmos.back.model.User;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.review.ReviewCategoryRepository;
import com.cosmos.back.repository.review.ReviewRepository;
import com.cosmos.back.repository.reviewplace.ReviewPlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PlaceRepository placeRepository;

    @MockBean
    private ReviewPlaceRepository reviewPlaceRepository;

    @MockBean
    private ReviewCategoryRepository reviewCategoryRepository;

    @Test
    @DisplayName("ReviewService 리뷰생성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    // service가 repository를 잘 호출 하는지, 알맞는 Response를 반환하는지 확인
    public void 리뷰생성() throws Exception {
        // 반환값이 넣어준 review의 id와 같은지

        // Review mock Dto
        ReviewRequestDto mockDto = ReviewRequestDto.builder().contents("가세요").build();
        Review mockReview = Review.builder().contents("가지 마세요").build();

        // User mock Dto
        User mockUser = User.builder().userSeq(1L).build();


        // Place mock Dto
        Place mockPlace = Place.builder().id(1L).build();

        // ReviewPlace mock Dto


        // ReviewCategory mock Dto



        when(reviewRepository.save(any())).thenReturn(mockReview);


        Long id = reviewService.createReview(mockDto);

        assertEquals(mockReview.getContents(), "가지 마세요");
    }
}