package com.cosmos.back.controller;

import com.cosmos.back.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "s3 file upload", description = "파일 업로드 테스트")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Service s3Service;

    @Operation(summary = "파일 업로드 테스트", description = "파일 업로드 테스트")
    @PostMapping("/file")
    public ResponseEntity<List<String>> uploadFile(@RequestPart("file") List<MultipartFile> multipartFile) {
        return ResponseEntity.ok(s3Service.uploadFiles(multipartFile));
    }
}