package com.cosmos.back.repository;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.config.TestConfig;
import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.Image;
import com.cosmos.back.model.Review;
import com.cosmos.back.model.ReviewPlace;
import com.cosmos.back.model.User;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.image.ImageRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.review.ReviewRepository;
import com.cosmos.back.repository.reviewplace.ReviewPlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import com.cosmos.back.service.ImageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TestConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewPlaceRepository reviewPlaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("ImageRepository 월별 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findMonthImageTest() throws Exception {

        //given
        User user = User.builder().reviews(new ArrayList<>()).coupleId(1L).build();
        userRepository.save(user);

        Place place = Place.builder().reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        Review review = Review.builder()
                .user(user)
                .reviewPlaces(new ArrayList<>())
                .reviewImages(new ArrayList<>())
                .build();
        reviewRepository.save(review);

        ReviewPlace reviewPlace = ReviewPlace.builder().place(place).review(review).build();
        reviewPlaceRepository.save(reviewPlace);

        Image image = Image.builder()
                .coupleId(user.getCoupleId())
                .review(review)
                .imageUrl("imageUrl")
                .createdTime("20230404").build();
        imageRepository.save(image);

        //when
        List<ImageResponseDto> result = imageRepository.findMonthImage(user.getCoupleId(), 202304L);
        System.out.println("result = " + result);
        //then
        assertThat(result.get(0).getImageUrl()).isEqualTo(image.getImageUrl());

    }

    @Test
    @DisplayName("ImageRepository 일별 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findDayImageTest() throws Exception {
        //given
        User user = User.builder().reviews(new ArrayList<>()).coupleId(1L).build();
        userRepository.save(user);

        Place place = Place.builder().reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        Review review = Review.builder()
                .user(user)
                .reviewPlaces(new ArrayList<>())
                .reviewImages(new ArrayList<>())
                .build();
        reviewRepository.save(review);

        ReviewPlace reviewPlace = ReviewPlace.builder().place(place).review(review).build();
        reviewPlaceRepository.save(reviewPlace);

        Image image = Image.builder()
                .coupleId(user.getCoupleId())
                .review(review)
                .imageUrl("imageUrl")
                .createdTime("20230404").build();
        imageRepository.save(image);

        //when
        List<ImageResponseDto> result = imageRepository.findDayImage(user.getCoupleId(), 20230404L);

        //then
        assertThat(result.get(0).getImageUrl()).isEqualTo(image.getImageUrl());

    }

    @Test
    @DisplayName("ImageRepository 일별 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findAllByCoupleIdTest() throws Exception {
        //given
        User user = User.builder().reviews(new ArrayList<>()).coupleId(1L).build();
        userRepository.save(user);

        Place place = Place.builder().reviewPlaces(new ArrayList<>()).build();
        placeRepository.save(place);

        Review review = Review.builder()
                .user(user)
                .reviewPlaces(new ArrayList<>())
                .reviewImages(new ArrayList<>())
                .build();
        reviewRepository.save(review);

        ReviewPlace reviewPlace = ReviewPlace.builder().place(place).review(review).build();
        reviewPlaceRepository.save(reviewPlace);

        Image image = Image.builder()
                .coupleId(user.getCoupleId())
                .review(review)
                .imageUrl("imageUrl")
                .createdTime("20230404").build();
        imageRepository.save(image);

        //when
        List<ImageResponseDto> result = imageRepository.findAllByCoupleId(user.getCoupleId(), 10, 0);

        //then
        assertThat(result.get(0).getImageUrl()).isEqualTo(image.getImageUrl());

    }

}
