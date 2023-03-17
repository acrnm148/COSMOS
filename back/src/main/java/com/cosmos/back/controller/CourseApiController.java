package com.cosmos.back.controller;

import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.MyCoursePlaceDto;
import com.cosmos.back.dto.request.CourseRequestDto;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.model.Course;
import com.cosmos.back.model.CoursePlace;
import com.cosmos.back.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "course", description = "코스 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CourseApiController {

    private final CourseService courseService;
    private final JwtService jwtService;

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

    @Operation(summary = "내 모든 코스 보기", description = "내 모든 코스 보기")
    @GetMapping("/courses/{userSeq}")
    public ResponseEntity<?> getMyAllCourses(@PathVariable("userSeq") Long userSeq,
                                                @RequestHeader(value = "Authorization") String token) {
        //userSeq 일치, access토큰 유효 여부 체크
        token = token.substring(7);
        JwtState state = jwtService.checkUserSeqWithAccess(userSeq, token);
        if (state.equals(JwtState.MISMATCH_USER)) { //userSeq 불일치
            return ResponseEntity.ok().body(jwtService.mismatchUserResponse());
        } else if (state.equals(JwtState.EXPIRED_ACCESS)) { //access 만료
            return ResponseEntity.ok().body(jwtService.requiredRefreshTokenResponse());
        }

        List<CourseResponseDto> courses = courseService.getMyAllCourses(userSeq);


        return new ResponseEntity<>( courses, HttpStatus.OK);
    }

    @Operation(summary = "내 코스 상세 보기", description = "내 코스 상세 보기")
    @GetMapping("/courses/{userSeq}/{courseId}")
    public ResponseEntity<?> getMyCourseDetail(@PathVariable("userSeq") Long userSeq,
                                             @PathVariable("courseId") Long courseId,
                                             @RequestHeader(value="Authorization") String token) {
        //userSeq 일치, access토큰 유효 여부 체크
        token = token.substring(7);
        JwtState state = jwtService.checkUserSeqWithAccess(userSeq, token);
        if (state.equals(JwtState.MISMATCH_USER)) { //userSeq 불일치
            return ResponseEntity.ok().body(jwtService.mismatchUserResponse());
        } else if (state.equals(JwtState.EXPIRED_ACCESS)) { //access 만료
            return ResponseEntity.ok().body(jwtService.requiredRefreshTokenResponse());
        }

        List<MyCoursePlaceDto> course = courseService.getMyCourseDetail(userSeq, courseId);

        return new ResponseEntity<>( course , HttpStatus.OK);
    }
}
