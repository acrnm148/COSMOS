package com.cosmos.back.controller;

import com.cosmos.back.dto.request.ImageRequestDto;
import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.Image;
import com.cosmos.back.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "image", description = "이미지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageApiController {

    private final ImageService imageService;

    @Operation(summary = "사진 등록", description = "S3서버에 사진을 등록하고 Url 반환, 다중 파일 등록 가능, MultipartForm으로 보낼 것")
    @PostMapping("/pictures/{coupleId}")
    public ResponseEntity<?> createImage(@RequestPart("file")List<MultipartFile> multipartFile, @PathVariable Long coupleId) {
        imageService.createImage(multipartFile, coupleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사진 삭제", description = "ImageId와 CoupleId를 Url 상으로 입력 필요")
    @DeleteMapping("/pictures/{imageId}/{coupleId}")
    public ResponseEntity<?> deleteImage(@PathVariable("imageId") Long imageId, @PathVariable("coupleId") Long coupleId) {
        imageService.deleteImage(imageId, coupleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사진 전체 조회", description = "coupleId Url 입력 시 해당 커플 사진 전체 조회가능")
    @GetMapping("/pictures/{coupleId}")
    public ResponseEntity<List> findTotalImage(@PathVariable Long coupleId, @RequestParam Integer limit, @RequestParam Integer offset) {
        List<ImageResponseDto> totalImage = imageService.findTotalImage(coupleId, limit, offset);
        return new ResponseEntity<>(totalImage, HttpStatus.OK);
    }

    @Operation(summary = "사진 월별 조회", description = "사진 월별을 조회함")
    @GetMapping("/pictures/month/{coupleId}")
    public ResponseEntity<List> findMonthImage(@PathVariable Long coupleId, @RequestParam Long month) {
        List<ImageResponseDto> monthImage = imageService.findMonthImage(coupleId, month);
        return new ResponseEntity<>(monthImage, HttpStatus.OK);
    }

    @Operation(summary = "사진 일별 조회", description = "사진 일별을 조회함")
    @GetMapping("/pictures/day/{coupleId}")
    public ResponseEntity<List> findDayImage(@PathVariable Long coupleId, @RequestParam Long day) {
        List<ImageResponseDto> DayImage = imageService.findDayImage(coupleId, day);
        return new ResponseEntity<>(DayImage, HttpStatus.OK);
    }
}
