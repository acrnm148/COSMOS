package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.dto.response.review.ReviewResponseDto;
import com.cosmos.back.dto.response.review.ReviewUserResponseDto;
import com.cosmos.back.model.*;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.image.ImageRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.review.*;
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
import java.util.Collections;
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

    @Autowired
    private ImageRepository imageRepository;

    @SpyBean
    private ReviewAdjectiveRepository reviewAdjectiveRepository;

    @SpyBean
    private ReviewNounRepository reviewNounRepository;

    @SpyBean
    private S3Service s3Service;

    @Autowired
    private ReviewCategoryRepository reviewCategoryRepository;

    @Autowired
    private IndiReviewCategoryRepository indiReviewCategoryRepository;

    @Test
    @DisplayName("CourseService createReview 메소드")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void createReview() {
        User user = User.builder().coupleId(1L).reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Place place = Place.builder().type("cafe").address("서울특별시 강남구").tendency("spo").reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));

        List<String> categories = new ArrayList<>();
        categories.add("뷰가 좋아요");

        List<String> indiCategories = new ArrayList<>();
        indiCategories.add("우정");

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("image url1");
        imageUrls.add("image url2");

        ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder().placeId(place.getId()).categories(categories).indiCategories(indiCategories).imageUrls(imageUrls).score(5).contents("리뷰 내용입니다.").contentsOpen(true).imageOpen(true).build();

        Long id = reviewService.createReview(reviewRequestDto, user.getUserSeq());

        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));

        assertEquals(review.getId(), id);
        assertEquals(review.getReviewCategories().get(0).getReviewCategoryCode(), "뷰가 좋아요");
    }

    @Test
    void deleteReview() {
        User user = User.builder().coupleId(1L).reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Place place = Place.builder().type("cafe").address("서울특별시 강남구").tendency("spo").reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        List<String> categories = new ArrayList<>();
        categories.add("뷰가 좋아요");

        List<String> indiCategories = new ArrayList<>();
        indiCategories.add("우정");

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("image url1");
        imageUrls.add("image url2");

        ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder().placeId(place.getId()).categories(categories).indiCategories(indiCategories).imageUrls(imageUrls).score(5).contents("리뷰 내용입니다.").contentsOpen(true).imageOpen(true).build();

        Long id = reviewService.createReview(reviewRequestDto, user.getUserSeq());

        reviewService.deleteReview(id, user.getUserSeq());

        List<Review> reviews = reviewRepository.findAllById(Collections.singleton(id));
        List<Image> images = imageRepository.findByReviewId(id);

        assertEquals(reviews.size(), 0);
        assertEquals(images.size(), 0);
    }

    @Test
    void findReviewsInPlaceUserCouple_커플인경우() {
        User user1 = User.builder().coupleId(1L).reviews(new ArrayList<>()).build();
        User user2 = User.builder().coupleId(1L).reviews(new ArrayList<>()).build();
        userRepository.save(user1);
        userRepository.save(user2);

        Place place = Place.builder().type("cafe").address("서울특별시 강남구").tendency("spo").reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        List<String> categories = new ArrayList<>();
        categories.add("뷰가 좋아요");

        List<String> indiCategories = new ArrayList<>();
        indiCategories.add("우정");

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("image url1");
        imageUrls.add("image url2");

        ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder().placeId(place.getId()).categories(categories).indiCategories(indiCategories).imageUrls(imageUrls).score(5).contents("리뷰 내용입니다.").contentsOpen(true).imageOpen(true).build();

        Long id1 = reviewService.createReview(reviewRequestDto, user1.getUserSeq());
        Long id2 = reviewService.createReview(reviewRequestDto, user2.getUserSeq());

        List<ReviewResponseDto> reviews  = reviewService.findReviewsInPlaceUserCouple(user1.getUserSeq(), user1.getCoupleId(), place.getId(), 10, 0);

        assertEquals(reviews.size(), 2);
        assertEquals(reviews.get(0).getReviewId(), id1);
        assertEquals(reviews.get(1).getReviewId(), id2);
    }
//
//    @Test
//    void findReviewsInPlaceUserCouple_솔로인경우() {
//        User user = User.builder().reviews(new ArrayList<>()).build();
//        userRepository.save(user);
//        System.out.println("user = " + user.getUserSeq());
//        Place place = Place.builder().type("cafe").address("서울특별시 강남구").tendency("spo").reviewPlaces(new ArrayList<>()).build();
//        placeRepository.save(place);
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
//        List<ReviewResponseDto> reviews  = reviewService.findReviewsInPlaceUserCouple(user.getUserSeq(), user.getCoupleId(), place.getId(), 10, 0);
//
//        assertEquals(reviews.size(), 1);
//        assertEquals(reviews.get(0).getReviewId(), id);
//    }

    @Test
    void findReviewsInPlace() {
        User user1 = User.builder().reviews(new ArrayList<>()).build();
        User user2 = User.builder().reviews(new ArrayList<>()).build();
        userRepository.save(user1);
        userRepository.save(user2);

        Place place = Place.builder().type("cafe").address("서울특별시 강남구").tendency("spo").reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        List<String> categories = new ArrayList<>();
        categories.add("뷰가 좋아요");

        List<String> indiCategories = new ArrayList<>();
        indiCategories.add("우정");

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("image url1");
        imageUrls.add("image url2");

        ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder().placeId(place.getId()).categories(categories).indiCategories(indiCategories).imageUrls(imageUrls).score(5).contents("리뷰 내용입니다.").contentsOpen(true).imageOpen(true).build();

        Long id1 = reviewService.createReview(reviewRequestDto, user1.getUserSeq());
        Long id2 = reviewService.createReview(reviewRequestDto, user2.getUserSeq());

        List<ReviewResponseDto> reviews = reviewService.findReviewsInPlace(place.getId(), 10, 0);

        assertEquals(reviews.size(), 2);
        assertEquals(reviews.get(0).getUserId(), user1.getUserSeq());
        assertEquals(reviews.get(1).getUserId(), user2.getUserSeq());
    }

    @Test
    void findReviewsInUser() {
        User user = User.builder().reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Place place1 = Place.builder().type("cafe").address("서울특별시 강남구").tendency("spo").reviewPlaces(new ArrayList<>()).build();
        Place place2 = Place.builder().type("restaurant").address("서울특별시 강남구").tendency("spo").reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place1);
        placeRepository.save(place2);

        List<String> categories = new ArrayList<>();
        categories.add("뷰가 좋아요");

        List<String> indiCategories = new ArrayList<>();
        indiCategories.add("우정");

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("image url1");
        imageUrls.add("image url2");

        ReviewRequestDto reviewRequestDto1 = ReviewRequestDto.builder().placeId(place1.getId()).categories(categories).indiCategories(indiCategories).imageUrls(imageUrls).score(5).contents("리뷰 내용입니다.").contentsOpen(true).imageOpen(true).build();
        ReviewRequestDto reviewRequestDto2 = ReviewRequestDto.builder().placeId(place2.getId()).categories(categories).indiCategories(indiCategories).imageUrls(imageUrls).score(5).contents("리뷰 내용입니다.").contentsOpen(true).imageOpen(true).build();

        Long id1 = reviewService.createReview(reviewRequestDto1, user.getUserSeq());
        Long id2 = reviewService.createReview(reviewRequestDto2, user.getUserSeq());

        List<ReviewUserResponseDto> reviews = reviewService.findReviewsInUser(user.getUserSeq(), 10, 0);

        assertEquals(reviews.size(), 2);
        assertEquals(reviews.get(0).getPlaceId(), place1.getId());
        assertEquals(reviews.get(1).getPlaceId(), place2.getId());
    }

    @Test
    void changeReview() {
        reset();
        doNothing().when(s3Service).deleteFile(anyString());

        User user = User.builder().reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Place place = Place.builder().type("cafe").address("서울특별시 강남구").tendency("spo").reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        List<String> categories = new ArrayList<>();
        categories.add("뷰가 좋아요");

        List<String> indiCategories = new ArrayList<>();
        indiCategories.add("우정");

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("image url1");
        imageUrls.add("image url2");

        ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder().placeId(place.getId()).categories(categories).indiCategories(indiCategories).imageUrls(imageUrls).score(5).contents("리뷰 내용입니다.").contentsOpen(true).imageOpen(true).build();

        Long id = reviewService.createReview(reviewRequestDto, user.getUserSeq());

        List<String> newCategories = new ArrayList<>();
        newCategories.add("맛이 좋아요");

        List<String> newIndiCategories = new ArrayList<>();
        newIndiCategories.add("사랑");

        List<String> newImageUrls = new ArrayList<>();
        newImageUrls.add("changed image url1");

        ReviewRequestDto newReviewRequestDto = ReviewRequestDto.builder().placeId(place.getId()).categories(newCategories).indiCategories(newIndiCategories).imageUrls(newImageUrls).contents("바뀐 리뷰 내용입니다").contentsOpen(false).imageOpen(false).build();
        Long newId = reviewService.changeReview(id, newReviewRequestDto, user.getUserSeq());

        Review review = reviewRepository.findById(newId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        assertEquals(review.getContents(), "바뀐 리뷰 내용입니다");
        assertEquals(review.getUser().getUserSeq(), user.getUserSeq());
    }

    @Test
    void findReviewNickName() {
        Adjective adjective = Adjective.builder().contents("mock adjective").build();
        List<Adjective> adjectives = new ArrayList<>();
        adjectives.add(adjective);

        when(reviewAdjectiveRepository.findAll()).thenReturn(adjectives);

        Noun noun = Noun.builder().contents("mock noun").build();
        List<Noun> nouns = new ArrayList<>();
        nouns.add(noun);

        when(reviewNounRepository.findAll()).thenReturn(nouns);

        String nickName = reviewService.findReviewNickName();

        assertEquals(nickName, "mock adjective mock noun");
    }
}