package com.cosmos.back.controller;

import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.request.*;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "course", description = "코스 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CourseApiController {

    private final CourseService courseService;
    private final JwtService jwtService;

    @Operation(summary = "코스 생성(추천 알고리즘)", description = "코스 생성")
    @PostMapping("/courses")
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto dto) {
        CourseResponseDto courseResponseDto = courseService.createCourse(dto);

        return new ResponseEntity<>(courseResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "코스 생성(사용자 생성)", description = "코스 생성")
    @PostMapping("/courses/users/{userSeq}")
    public ResponseEntity<Long> createCourseByUser(@PathVariable Long userSeq, @RequestBody CouserUesrRequestDto dto) {
        Long courseId = courseService.createCourseByUser(userSeq, dto);

        return new ResponseEntity<>(courseId, HttpStatus.OK);
    }

    @Operation(summary = "코스 찜", description = "코스 찜")
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<Long> likeCourse(@PathVariable Long courseId) {
        Map<String, String> map = courseService.likeCourse(courseId);

        return new ResponseEntity<>(Long.parseLong(map.get("courseId")), HttpStatus.OK);
    }

    @Operation(summary = "코스 찜 삭제", description = "코스 찜 삭제")
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Long> deleteCourse(@PathVariable Long courseId) {
        Long id = courseService.deleteCourse(courseId);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "내 찜한 코스 보기", description = "내 찜한 코스 보기")
    @GetMapping("/courses/users/{userSeq}")
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

        List<CourseResponseDto> courses = courseService.listLikeCourse(userSeq);


        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @Operation(summary = "내 코스 상세 보기", description = "내 코스 상세 보기")
    @GetMapping("/courses/{courseId}/users/{userSeq}")
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

        CourseResponseDto courseResponseDto = courseService.getMyCourseDetail(courseId);

        return new ResponseEntity<>(courseResponseDto , HttpStatus.OK);
    }

    @Operation(summary = "코스 내용 수정", description = "코스 내용 중 name만 수정")
    @PutMapping("/courses/{courseId}/contents")
    public ResponseEntity<Long> updateCourseContents(@PathVariable Long courseId, @RequestBody CourseUpdateContentsRequestDto dto) {
        Long resultCourseId = courseService.updateCourseContents(courseId, dto);
        return new ResponseEntity<>(resultCourseId, HttpStatus.OK);
    }


    @Operation(summary = "코스 수정(추가)", description = "코스 장소 추가(코스에 포함된 장소들 중에서 마지막에 추가된다)")
    @PutMapping("/courses/{courseId}/add")
    public ResponseEntity<Long> updateCourseAdd(@PathVariable Long courseId, @RequestBody CourseUpdateAddDelRequestDto dto) {
        Long placeId = courseService.updateCourseAdd(courseId, dto);
        return new ResponseEntity<>(placeId, HttpStatus.OK);
    }

    @Operation(summary = "코스 수정(삭제)", description = "코스 장소 삭제")
    @PutMapping("/courses/{courseId}/delete")
    public ResponseEntity<Long> updateCourseDelete(@PathVariable Long courseId, @RequestBody CourseUpdateAddDelRequestDto dto) {
        Long placeId = courseService.updateCourseDelete(courseId, dto);
        return new ResponseEntity<>(placeId, HttpStatus.OK);
    }

    @Operation(summary = "코스 수정(순서)", description = "코스 장소 순서 변경")
    @PutMapping("/courses/{courseId}/orders")
    public ResponseEntity<Long> updateCourseOrders(@PathVariable Long courseId, @RequestBody CourseUpdateOrdersRequestDto dto) {
        Long id = courseService.updateCourseOrders(courseId, dto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
