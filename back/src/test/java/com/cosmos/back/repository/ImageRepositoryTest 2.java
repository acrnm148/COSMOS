package com.cosmos.back.repository;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.config.TestConfig;
import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.Image;
import com.cosmos.back.model.Review;
import com.cosmos.back.model.User;
import com.cosmos.back.repository.image.ImageRepository;
import com.cosmos.back.repository.review.ReviewRepository;
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

    @Test
    @DisplayName("ImageRepository 월별 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findMonthImageTest() throws Exception {
        //given
        Image image = Image.builder().id(1L).coupleId(1L).imageUrl("imageUrl").createdTime("20220404").build();
        imageRepository.save(image);

        //when
        List<ImageResponseDto> result = imageRepository.findMonthImage(1L, 202204L);

        //then
        assertThat(result.get(0).getImageUrl()).isEqualTo(image.getImageUrl());

    }

    @Test
    @DisplayName("ImageRepository 일별 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findDayImageTest() throws Exception {
        //given
        Image image = Image.builder().coupleId(1L).imageUrl("imageUrl").createdTime("20230404").build();
        imageRepository.save(image);

        //when
        List<ImageResponseDto> result = imageRepository.findMonthImage(1L, 20230404L);

        //then
        assertThat(result.get(0).getImageUrl()).isEqualTo(image.getImageUrl());

    }

    @Test
    @DisplayName("ImageRepository 일별 상세 정보 받아오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findAllByCoupleIdTest() throws Exception {
        //given
        User user = User.builder().reviews(new ArrayList<>()).coupleId(1L).build();
        Review review = Review.builder()
                .user(user)
                .reviewImages(new ArrayList<>())
                .id(1L).build();
        reviewRepository.save(review);
        Image image = Image.builder()
                .coupleId(1L)
                .review(review)
                .imageUrl("imageUrl")
                .createdTime("20230404").build();
        imageRepository.save(image);

        //when
        List<Image> result = imageRepository.findAllByCoupleId(1L, 10, 0);

        //then
        assertThat(result.get(0).getImageUrl()).isEqualTo(image.getImageUrl());

    }

}
