package com.cosmos.back.controller;

import com.cosmos.back.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "course", description = "코스 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CourseApiController {

    private final CourseService courseService;

    @Operation(summary = "코스 생성", description = "코스 생성")
    @GetMapping("/courses")
    public ResponseEntity<> createCourse() {}
}
