package com.cosmos.back.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.cosmos.back.annotation.EnableMockMvc;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
public class S3ServiceTest {

    @Autowired
    private S3Service s3Service;

    @MockBean
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AmazonS3 amazonS3;

    @Test
    @DisplayName("S3 Upload")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void uploadFilesTest() throws Exception {
        //given
        List<MultipartFile> multipartFileList = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:/static/test.PNG");
        MockMultipartFile file = new MockMultipartFile("file",
                "test.PNG",
                "image/png",
                new FileInputStream(resource.getURI().getPath()));
        multipartFileList.add(file);

        PutObjectResult putObjectResult = new PutObjectResult();
        when(amazonS3Client.putObject(eq(bucket), eq(file.getOriginalFilename()), eq(file.getInputStream()), any(ObjectMetadata.class))).thenReturn(putObjectResult);

        //when
        List<String> list = s3Service.uploadFiles(multipartFileList);

        //then
        Assertions.assertThat(list.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("S3 deleteFile")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void deleteFileTest() throws Exception{
        //given
        String fileName = "test";
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);

        when(amazonS3Client.doesObjectExist(eq(bucket), eq(fileName))).thenReturn(true);

        //when
        s3Service.deleteFile(fileName);

        //then
        verify(amazonS3Client, times(1)).doesObjectExist(eq(bucket), eq(fileName));
    }
}
