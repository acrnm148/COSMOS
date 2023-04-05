package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.model.Review;
import com.cosmos.back.model.User;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.review.ReviewRepository;
import com.cosmos.back.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
@Transactional
class ReviewServiceTest {

    @SpyBean
    private UserRepository userRepository;

    @SpyBean
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("CourseService createReview 메소드")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void createReview() {
//        User user = User.builder().coupleId(1L).build();
//        userRepository.save(user);
//
//        Place place = Place.builder().type("cafe").address("서울특별시 강남구").tendency("spo").build();
//        placeRepository.save(place);
//
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));
//
//        List<String> categories = new ArrayList<>();
//        categories.add("뷰가 좋아요");
//
//        List<String> indiCategories = new ArrayList<>();
//        indiCategories.add("우정");
//
//        List<String> imageUrls = new ArrayList<>();
//        imageUrls.add("image url1");
//        imageUrls.add("image url2");
//
//        ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder().placeId(place.getId()).categories(categories).indiCategories(indiCategories).imageUrls(imageUrls).score(5).contents("리뷰 내용입니다.").contentsOpen(true).imageOpen(true).build();
//
//        Long id = reviewService.createReview(reviewRequestDto, user.getUserSeq());
//
//        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
//
//        assertEquals();
    }

    @Test
    void deleteReview() {
    }

    @Test
    void findReviewsInPlaceUserCouple() {
    }

    @Test
    void findReviewsInPlace() {
    }

    @Test
    void findReviewsInUser() {
    }

    @Test
    void changeReview() {
    }

    @Test
    void findReviewNickName() {
    }
}