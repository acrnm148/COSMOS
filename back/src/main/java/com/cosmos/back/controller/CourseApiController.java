package com.cosmos.back.controller;

import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "course", description = "코스 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CourseApiController {

    private final CourseService courseService;

    @Operation(summary = "코스 생성", description = "코스 생성")
    @PostMapping("/courses")
    public ResponseEntity<Long> createCourse(@RequestBody CourseRequestDto dto) {
        Long courseId = courseService.createCourse(dto);

        return new ResponseEntity<>(courseId, HttpStatus.OK);
    }

    @Operation(summary = "코스 삭제", description = "코스 삭제")
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Long> deleteCourse(@PathVariable Long courseId) {
        Long id = courseService.deleteCourse(courseId);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
