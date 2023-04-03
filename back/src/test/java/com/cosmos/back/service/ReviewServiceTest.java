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
}