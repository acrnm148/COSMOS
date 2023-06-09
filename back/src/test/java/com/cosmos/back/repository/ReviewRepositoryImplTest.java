package com.cosmos.back.repository;

import com.cosmos.back.config.TestConfig;
import com.cosmos.back.model.*;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.image.ImageRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.review.IndiReviewCategoryRepository;
import com.cosmos.back.repository.review.ReviewCategoryRepository;
import com.cosmos.back.repository.review.ReviewRepository;
import com.cosmos.back.repository.reviewplace.ReviewPlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TestConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryImplTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewPlaceRepository reviewPlaceRepository;

    @Autowired
    private IndiReviewCategoryRepository indiReviewCategoryRepository;

    @Autowired
    private ReviewCategoryRepository reviewCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("ReviewRepositoryImpl 리뷰 ID로 리뷰 삭제하기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void deleteReviewQueryDsl() {
        User user = User.builder().userName("mock user").reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Review review1 = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname1", true, true);
        Review review2 = Review.createReview(user, "mock contents", 4, "20230404", "mock nickname2", true, true);

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        Long execute1 = reviewRepository.deleteReviewQueryDsl(review1.getId());
        Long execute2 = reviewRepository.deleteReviewQueryDsl(review1.getId());

        assertThat(execute1).isEqualTo(1);
        assertThat(execute2).isEqualTo(0);
    }

    @Test
    @DisplayName("ReviewRepositoryImpl 리뷰 ID로 리뷰 카테고리 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void deleteReviewCategoryQueryDsl() {
        User user = User.builder().userName("mock user").reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Review review = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname", true, true);

        reviewRepository.save(review);

        ReviewCategory reviewCategory1 = ReviewCategory.createReviewCategory("뷰가 좋아요", review);
        ReviewCategory reviewCategory2 = ReviewCategory.createReviewCategory("맛이 좋아요", review);

        reviewCategoryRepository.save(reviewCategory1);
        reviewCategoryRepository.save(reviewCategory2);

        Long execute1 = reviewRepository.deleteReviewCategoryQueryDsl(review.getId());
        Long execute2 = reviewRepository.deleteReviewCategoryQueryDsl(review.getId());

        assertThat(execute1).isEqualTo(2);
        assertThat(execute2).isEqualTo(0);
    }

    @Test
    @DisplayName("ReviewRepositoryImpl 리뷰 ID로 Indi 리뷰 카테고리 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void deleteIndiReviewCategoryQueryDsl() {
        User user = User.builder().userName("mock user").reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Review review = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname", true, true);

        reviewRepository.save(review);

        IndiReviewCategory reviewCategory1 = IndiReviewCategory.createIndiReviewCategory("뷰가 좋아요", review);
        IndiReviewCategory reviewCategory2 = IndiReviewCategory.createIndiReviewCategory("맛이 좋아요", review);

        indiReviewCategoryRepository.save(reviewCategory1);
        indiReviewCategoryRepository.save(reviewCategory2);

        Long execute1 = reviewRepository.deleteIndiReviewCategoryQueryDsl(review.getId());
        Long execute2 = reviewRepository.deleteIndiReviewCategoryQueryDsl(review.getId());

        assertThat(execute1).isEqualTo(2);
        assertThat(execute2).isEqualTo(0);
    }

    @Test
    @DisplayName("ReviewRepositoryImpl 유저 Seq로 모든 리뷰 가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void findReviewByUserSeq() {
        User user = User.builder().userName("mock user").reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Review review1 = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname", true, true);
        Review review2 = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname", true, true);

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> reviews = reviewRepository.findReviewByUserSeq(user.getUserSeq());

        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getId()).isEqualTo(review1.getId());
        assertThat(reviews.get(1).getId()).isEqualTo(review2.getId());
    }

    @Test
    @DisplayName("ReviewRepositoryImpl 리뷰 ID로 reviewPlace 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void deleteReviewPlaceQueryDsl() {
        User user = User.builder().userName("mock user").reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Review review = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname", true, true);

        reviewRepository.save(review);

        Place place = Place.builder().reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        ReviewPlace reviewPlace = ReviewPlace.createReviewPlace(review, place);
        reviewPlaceRepository.save(reviewPlace);

        Long execute1 = reviewRepository.deleteReviewPlaceQueryDsl(review.getId());
        Long execute2 = reviewRepository.deleteReviewPlaceQueryDsl(review.getId());

        assertThat(execute1).isEqualTo(1);
        assertThat(execute2).isEqualTo(0);
    }

    @Test
    @DisplayName("ReviewRepositoryImpl 리뷰 ID로 image 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void deleteReviewImagesQueryDsl() {
        User user = User.builder().userName("mock user").reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Review review = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname", true, true);

        reviewRepository.save(review);

        Image image = Image.createImage("image url", 1L, review);
        imageRepository.save(image);

        Long execute1 = reviewRepository.deleteReviewImagesQueryDsl(review.getId());
        Long execute2 = reviewRepository.deleteReviewImagesQueryDsl(review.getId());

        assertThat(execute1).isEqualTo(1);
        assertThat(execute2).isEqualTo(0);
    }

    @Test
    @DisplayName("ReviewRepositoryImpl 장소별로 유저 Seq를 이용해 리뷰 모두 불러오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void findReviewInPlaceUserCoupleQueryDsl() {
        User user = User.builder().userName("mock user").reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Review review1 = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname1", true, true);
        Review review2 = Review.createReview(user, "mock contents", 4, "20230404", "mock nickname2", true, false);

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        Image image1 = Image.createImage("image url 1", 1L, review1);
        Image image2 = Image.createImage("image url 2", 1L, review2);

        imageRepository.save(image1);
        imageRepository.save(image2);


        Place place = Place.builder().reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        ReviewPlace reviewPlace1 = ReviewPlace.createReviewPlace(review1, place);
        ReviewPlace reviewPlace2 = ReviewPlace.createReviewPlace(review2, place);

        reviewPlaceRepository.save(reviewPlace1);
        reviewPlaceRepository.save(reviewPlace2);

        List<Review> reviews = reviewRepository.findReviewInPlaceUserCoupleQueryDsl(user.getUserSeq(), place.getId(), 10, 0);

        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getId()).isEqualTo(review1.getId());
        assertThat(reviews.get(1).getId()).isEqualTo(review2.getId());
    }

    @Test
    @DisplayName("ReviewRepositoryImpl 장소 ID로 모든 리뷰 불러오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void findReviewInPlaceQueryDsl() {
        User user = User.builder().userName("mock user").reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Review review1 = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname1", true, true);
        Review review2 = Review.createReview(user, "mock contents", 4, "20230404", "mock nickname2", true, false);

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        Image image1 = Image.createImage("image url 1", 1L, review1);
        Image image2 = Image.createImage("image url 2", 1L, review2);

        imageRepository.save(image1);
        imageRepository.save(image2);


        Place place = Place.builder().reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        ReviewPlace reviewPlace1 = ReviewPlace.createReviewPlace(review1, place);
        ReviewPlace reviewPlace2 = ReviewPlace.createReviewPlace(review2, place);

        reviewPlaceRepository.save(reviewPlace1);
        reviewPlaceRepository.save(reviewPlace2);

        ReviewCategory reviewCategory1 = ReviewCategory.createReviewCategory("뷰가 좋아요", review1);
        ReviewCategory reviewCategory2 = ReviewCategory.createReviewCategory("맛이 좋아요", review2);

        reviewCategoryRepository.save(reviewCategory1);
        reviewCategoryRepository.save(reviewCategory2);

        IndiReviewCategory indiReviewCategory1 = IndiReviewCategory.createIndiReviewCategory("사랑", review1);
        IndiReviewCategory indiReviewCategory2 = IndiReviewCategory.createIndiReviewCategory("우정", review2);

        indiReviewCategoryRepository.save(indiReviewCategory1);
        indiReviewCategoryRepository.save(indiReviewCategory2);

        List<Review> reviews = reviewRepository.findReviewInPlaceQueryDsl(place.getId(), 10, 0);

        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getId()).isEqualTo(review1.getId());
        assertThat(reviews.get(1).getId()).isEqualTo(review2.getId());
        assertThat(reviews.get(0).getReviewCategories().get(0).getReviewCategoryCode()).isEqualTo("뷰가 좋아요");
        assertThat(reviews.get(0).getIndiReviewCategories().get(0).getReviewCategory()).isEqualTo("사랑");
    }

    @Test
    @DisplayName("ReviewRepositoryImpl 유저 ID로 내 리뷰 모두 불러오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    void findReviewInUserQueryDsl() {
        User user = User.builder().userName("mock user").reviews(new ArrayList<>()).build();
        userRepository.save(user);

        Review review1 = Review.createReview(user, "mock contents", 5, "20230404", "mock nickname1", true, true);
        Review review2 = Review.createReview(user, "mock contents", 4, "20230404", "mock nickname2", true, false);

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        Image image1 = Image.createImage("image url 1", 1L, review1);
        Image image2 = Image.createImage("image url 2", 1L, review2);

        imageRepository.save(image1);
        imageRepository.save(image2);


        Place place = Place.builder().reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        ReviewPlace reviewPlace1 = ReviewPlace.createReviewPlace(review1, place);
        ReviewPlace reviewPlace2 = ReviewPlace.createReviewPlace(review2, place);

        reviewPlaceRepository.save(reviewPlace1);
        reviewPlaceRepository.save(reviewPlace2);

        ReviewCategory reviewCategory1 = ReviewCategory.createReviewCategory("뷰가 좋아요", review1);
        ReviewCategory reviewCategory2 = ReviewCategory.createReviewCategory("맛이 좋아요", review2);

        reviewCategoryRepository.save(reviewCategory1);
        reviewCategoryRepository.save(reviewCategory2);

        IndiReviewCategory indiReviewCategory1 = IndiReviewCategory.createIndiReviewCategory("사랑", review1);
        IndiReviewCategory indiReviewCategory2 = IndiReviewCategory.createIndiReviewCategory("우정", review2);

        indiReviewCategoryRepository.save(indiReviewCategory1);
        indiReviewCategoryRepository.save(indiReviewCategory2);

        List<Review> reviews = reviewRepository.findReviewInUserQueryDsl(user.getUserSeq(), 10, 0);

        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getUser().getUserSeq()).isEqualTo(user.getUserSeq());
        assertThat(reviews.get(1).getUser().getUserSeq()).isEqualTo(user.getUserSeq());
    }
}