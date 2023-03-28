package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
public class ImageControllerTest {

    @Value ("${file.path}")
    private String rootUrl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ImageService imageService;

//    @Test
//    @DisplayName("사진 등록하기")
//    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
//    public void createImageTest() throws Exception {
//        System.out.println(rootUrl+"test.PNG");
//
////        ClassPathResource resource = new ClassPathResource("/static/test.PNG");
////        FileInputStream fileI = new FileInputStream("static/test.PNG");
////        System.out.println("fileI = " + fileI);
//
//
//        //give
//        doNothing().when(imageService).createImage(anyList(), anyLong());
//        MockMultipartFile file = new MockMultipartFile("file",
//                "test.PNG",
//                "image/png",
//                new FileInputStream(rootUrl+"test.PNG"));
//
//        //when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/pictures/1").file(file));
//
//        //then
//        resultActions.andExpect(status().isOk());
//    }
}
