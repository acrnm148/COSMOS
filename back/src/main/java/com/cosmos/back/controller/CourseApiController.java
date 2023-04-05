package com.cosmos.back.controller;

import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.request.*;
import com.cosmos.back.dto.response.CourseResponseDto;
import com.cosmos.back.service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto dto) throws JsonProcessingException {
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
    @PutMapping("/courses")
    public ResponseEntity<Long> likeCourse(@RequestBody CourseNameRequestDto dto) {
        Map<String, String> map = courseService.likeCourse(dto.getCourseId(), dto.getName());

        // 알림 전송
        //notificationService.send("makeWish", user.getCoupleUserSeq(), coupleUserName+"님이 장소 "+ place.getName() +"을(를) 찜하셨습니다.");

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

    @Operation(summary = "코스 수정(찜한 코스 커스텀)", description = "코스 전체 수정")
    @PutMapping("/courses/{courseId}/coursechange")
    public ResponseEntity<Long> updateCourseFinal(@PathVariable Long courseId, @RequestBody CourseUpdateRequstDto courseUpdateRequstDto) {
        Long id = courseService.updateCourse(courseId, courseUpdateRequstDto);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
