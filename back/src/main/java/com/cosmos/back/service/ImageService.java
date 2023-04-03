package com.cosmos.back.service;

import com.cosmos.back.annotation.Generated;
import com.cosmos.back.annotation.RedisCached;
import com.cosmos.back.annotation.RedisCachedKeyParam;
import com.cosmos.back.annotation.RedisEvict;
import com.cosmos.back.config.RedisDB;
import com.cosmos.back.dto.request.ImageRequestDto;
import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.Image;
import com.cosmos.back.model.Review;
import com.cosmos.back.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    // 사진 생성
    @Transactional
    @RedisEvict(key = "image")
    @Generated
    public void createImage(List<MultipartFile> multipartFile, @RedisCachedKeyParam(key = "coupleId")Long coupleId) {
        List<String> imageUrls = s3Service.uploadFiles(multipartFile);
        Review review = Review.builder().reviewImages(new ArrayList<>()).build();

        for (String imageUrl:imageUrls) {
            Image image = Image.createImage(imageUrl, coupleId, review);
            imageRepository.save(image);
        }
    }

    // 사진 삭제
    @Transactional
    @RedisEvict(key = "image")
    public void deleteImage(Long imageId, @RedisCachedKeyParam(key = "coupleId")Long coupleId) {
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new NoSuchElementException("no such data"));
        String imageUrl = image.getImageUrl();
        String[] urls = imageUrl.split("/");
        String fileName = urls[urls.length - 1];
        s3Service.deleteFile(fileName);
        imageRepository.deleteById(imageId);
    }

    // 사진 전체 조회
    @RedisCached(key = "image", expire = 240)
    public List<ImageResponseDto> findTotalImage(@RedisCachedKeyParam(key = "coupleId") Long coupleId, Integer limit, Integer offset) {
        List<Image> allByCoupleId = imageRepository.findAllByCoupleId(coupleId, limit, offset);

        List<ImageResponseDto> list = new ArrayList<>();
        for (Image i : allByCoupleId) {
            ImageResponseDto dto = ImageResponseDto.builder()
                    .imageId(i.getId())
                    .imageUrl(i.getImageUrl())
                    .createdTime(i.getCreatedTime())
                    .reviewId(i.getReview().getId())
                    .build();
            list.add(dto);
        }

        return list;
    }

    // 사진 월별 조회
    @Generated
    public List<ImageResponseDto> findMonthImage(Long coupleId, Long month) {

        List<ImageResponseDto> monthImage = imageRepository.findMonthImage(coupleId, month);
        return monthImage;
    }

    // 사진 일별 조회
    @Generated
    public List<ImageResponseDto> findDayImage(Long coupleId, Long day) {

        List<ImageResponseDto> dayImage = imageRepository.findDayImage(coupleId, day);
        return dayImage;
    }
}
