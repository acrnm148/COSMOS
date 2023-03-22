package com.cosmos.back.service;

import com.cosmos.back.dto.request.ImageRequestDto;
import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.Image;
import com.cosmos.back.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;

    // 사진 생성
    @Transactional
    public void createImage(ImageRequestDto dto) {
        String imageUrl = dto.getImageUrl();
        Long coupleId = dto.getCoupleId();

        Image image = Image.createImage(imageUrl, coupleId);
        imageRepository.save(image);
    }

    // 사진 삭제
    @Transactional
    public void deleteImage(Long imageId) {
        imageRepository.deleteById(imageId);
    }

    // 사진 전체 조회
    public List<ImageResponseDto> findTotalImage(Long coupleId) {
        List<Image> allByCoupleId = imageRepository.findAllByCoupleId(coupleId);

        List<ImageResponseDto> list = new ArrayList<>();
        for (Image i : allByCoupleId) {
            ImageResponseDto dto = ImageResponseDto.builder()
                    .imageId(i.getId())
                    .imageUrl(i.getImageUrl())
                    .build();
            list.add(dto);
        }

        return list;
    }

    // 사진 월별 조회
    public List<ImageResponseDto> findMonthImage(Long coupleId, Long month) {

        List<ImageResponseDto> monthImage = imageRepository.findMonthImage(coupleId, month);
        return monthImage;
    }

    // 사진 일별 조회
    public List<ImageResponseDto> findDayImage(Long coupleId, Long day) {

        List<ImageResponseDto> dayImage = imageRepository.findDayImage(coupleId, day);
        return dayImage;
    }
}
