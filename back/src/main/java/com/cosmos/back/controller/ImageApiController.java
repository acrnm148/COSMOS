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

import java.util.List;

@Tag(name = "image", description = "이미지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageApiController {

    private final ImageService imageService;

    @Operation(summary = "사진 등록", description = "사진을 등록함")
    @PostMapping("/pictures")
    public ResponseEntity<?> createImage(@RequestBody ImageRequestDto dto) {
        imageService.createImage(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사진 삭제", description = "사진을 삭제함")
    @DeleteMapping("/pictures/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사진 전체 조회", description = "사진 전체를 조회함")
    @GetMapping("/pictures/{coupleId}")
    public ResponseEntity<List> findTotalImage(@PathVariable Long coupleId) {
        List<ImageResponseDto> totalImage = imageService.findTotalImage(coupleId);
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
