package com.cosmos.back.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 다중 파일 업로드
     */
    public List<String> uploadFiles(List<MultipartFile> multipartFile) {
        List<String> fileNameList = new ArrayList<>();

        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        multipartFile.forEach(file -> {
            try {
                String fileName = createFileName(file.getOriginalFilename());
                String fileUrl= "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" +fileName;
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(file.getContentType());
                objectMetadata.setContentLength(file.getSize());

                amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
                fileNameList.add(fileUrl);
            } catch(IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
        });

        return fileNameList;
    }

    private String createFileName(String fileName) { // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        UUID uuid = UUID.randomUUID();
        String[] splitString = fileName.split("\\.");
        String ext = splitString[splitString.length -1];
        String changedFileName = uuid.toString() +"."+ext;

        return changedFileName;
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, fileName);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
        s3.deleteObject(request);
    }

}
