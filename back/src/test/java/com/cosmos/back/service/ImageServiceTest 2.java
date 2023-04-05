package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.Image;
import com.cosmos.back.model.Review;
import com.cosmos.back.repository.image.ImageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
public class ImageServiceTest {

    @MockBean
    private S3Service s3Service;

    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    @DisplayName("createImage 사진 생성")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void createImageTest() throws Exception{
        //given
        // imageUrls 결과 Mock 데이터
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("imageUrl");
        }

        Resource resource = resourceLoader.getResource("classpath:/static/test.PNG");

        // MultipartFile Mock 데이터 생성
        List<MultipartFile> multipartFileList = new ArrayList<>();
        MultipartFile multipartFile = new MockMultipartFile("file",
                "test.PNG",
                "image/png",
                new FileInputStream(resource.getURI().getPath()));
        for (int i = 0; i < 5; i++) {
            multipartFileList.add(multipartFile);
        }

        Image image = Image.builder().build();

        when(s3Service.uploadFiles(anyList())).thenReturn(list);

        //when
        imageService.createImage(multipartFileList, 1L);

        //then
        verify(s3Service, times(1)).uploadFiles(multipartFileList);
        verify(imageRepository, times(5)).save(any(Image.class));
    }

    @Test
    @DisplayName("deleteImage 사진 삭제")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void deleteImageTest() throws Exception{
        //given
        Image image = Image.builder().imageUrl("testUrl").build();
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        doNothing().when(s3Service).deleteFile(anyString());
        doNothing().when(imageRepository).deleteById(anyLong());

        //when
        imageService.deleteImage(1L, 1L);

        //then
        verify(imageRepository, times(1)).findById(anyLong());
        verify(s3Service, times(1)).deleteFile(anyString());
        verify(imageRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("findTotalImage 사진 전체 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findTotalImageTest() throws Exception{
        //given
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Review review = Review.builder().id((long) i).build();
            Image image = Image.builder().id((long) i).imageUrl("imgUrl").createdTime("20230403").review(review).build();
            image.setId((long) i);
            list.add(image);
        }

        when(imageRepository.findAllByCoupleId(1L, 10, 0)).thenReturn(list);

        //when
        List<ImageResponseDto> totalImages = imageService.findTotalImage(1L, 10, 0);

        //then
        assertEquals(totalImages.size(), 5);
        verify(imageRepository, times(1)).findAllByCoupleId(1L, 10, 0);
    }

    @Test
    @DisplayName("findMonthImage 사진 월별 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findMonthImageTest() throws Exception{
        //given
        List<ImageResponseDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ImageResponseDto imageResponseDto = ImageResponseDto.builder().build();
            imageResponseDto.setImageId((long) i);
            list.add(imageResponseDto);
        }
        when(imageRepository.findMonthImage(anyLong(), anyLong())).thenReturn(list);

        //when
        List<ImageResponseDto> monthImages = imageService.findMonthImage(1L, 202303L);

        //then
        assertEquals(monthImages.size(), 5);
        assertThat(monthImages.get(0).getImageId()).isEqualTo(0);
    }

    @Test
    @DisplayName("findDayImage 사진 일별 조회")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void findDayImageTest() throws Exception{
        //given
        List<ImageResponseDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ImageResponseDto imageResponseDto = ImageResponseDto.builder().build();
            imageResponseDto.setImageId((long) i);
            list.add(imageResponseDto);
        }
        when(imageRepository.findDayImage(anyLong(), anyLong())).thenReturn(list);

        //when
        List<ImageResponseDto> dayImages = imageService.findDayImage(1L, 20230328L);

        //then
        assertEquals(dayImages.size(), 5);
        assertThat(dayImages.get(0).getImageId()).isEqualTo(0);

    }

}
