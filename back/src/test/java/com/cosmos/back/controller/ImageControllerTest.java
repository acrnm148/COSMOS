package com.cosmos.back.controller;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@EnableMockMvc
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ImageService imageService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    @DisplayName("사진 등록하기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void createImageTest() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:/static/test.PNG");
        System.out.println("resource = " + resource);
        //give
        doNothing().when(imageService).createImage(anyList(), anyLong());
        MockMultipartFile file = new MockMultipartFile("file",
                "test.PNG",
                "image/png",
                Files.newInputStream(Paths.get(resource.getURI().getPath())));
                //new FileInputStream(resource.getURI().getPath()));

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/pictures/1").file(file));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("사진 삭제하기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void deleteImageTest() throws Exception {
        //given
        doNothing().when(imageService).deleteImage(anyLong(), anyLong());

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/pictures/1/1"));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("사진 전체 조회")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void findTotalImageTest() throws Exception {
        //given
        List<ImageResponseDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            list.add(ImageResponseDto.builder().imageId(new Long(i)).build());
        }

        when(imageService.findTotalImage(anyLong())).thenReturn(list);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/pictures/1"));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();

        ImageResponseDto[] dto = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), ImageResponseDto[].class);
        List<ImageResponseDto> data = Arrays.asList(dto);

        assertEquals(data.size(), 5);
        assertThat(data.get(0).getImageId()).isEqualTo(0);
    }

    @Test
    @DisplayName("사진 월별 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findMonthImageTest() throws Exception {
        //given
        List<ImageResponseDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            list.add(ImageResponseDto.builder().imageId(new Long(i)).build());
        }

        when(imageService.findMonthImage(anyLong(), anyLong())).thenReturn(list);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/pictures/month/1?month=202303"));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        ImageResponseDto[] dto = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), ImageResponseDto[].class);
        List<ImageResponseDto> data = Arrays.asList(dto);

        assertEquals(data.size(), 5);
        assertThat(data.get(0).getImageId()).isEqualTo(0);
    }

    @Test
    @DisplayName("사진 일별 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findDayImageTest() throws Exception {
        //given
        List<ImageResponseDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            list.add(ImageResponseDto.builder().imageId(new Long(i)).build());
        }

        when(imageService.findDayImage(anyLong(), anyLong())).thenReturn(list);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/pictures/day/1?day=20230328"));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        ImageResponseDto[] dto = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), ImageResponseDto[].class);
        List<ImageResponseDto> data = Arrays.asList(dto);

        assertEquals(data.size(), 5);
        assertThat(data.get(0).getImageId()).isEqualTo(0);
    }
}
