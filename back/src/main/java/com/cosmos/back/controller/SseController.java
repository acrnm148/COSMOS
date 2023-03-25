package com.cosmos.back.controller;

import com.cosmos.back.auth.jwt.JwtState;
import com.cosmos.back.auth.jwt.service.JwtService;
import com.cosmos.back.dto.request.NotificationRequestDto;
import com.cosmos.back.dto.response.NotificationDto;
import com.cosmos.back.dto.user.UserProfileDto;
import com.cosmos.back.model.NotificationType;
import com.cosmos.back.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SseController {

    private final NotificationService notificationService;
    private final JwtService jwtService;

    @Operation(summary = "알림 구독(SSE에 연결)", description = "알림을 구독한다.(SSE에 연결)")
    @GetMapping(value = "/noti/subscribe/{userSeq}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable("userSeq") Long userSeq,
                                @RequestHeader(value = "lastEventId", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(userSeq, lastEventId);
    }

    @Operation(summary = "알림 조회", description = "알림을 모두 조회한다.")
    @GetMapping(value = "/noti/list/{userSeq}")
    public ResponseEntity<?> findAllNotifications(@PathVariable("userSeq") Long userSeq) {
        List<NotificationDto> notifications = notificationService.findAllNotifications(userSeq);
        return ResponseEntity.ok().body(notifications);
    }

    @Operation(summary = "안읽은 알림 개수", description = "안읽은 알림 개수를 리턴한다.")
    @GetMapping(value = "/noti/unread/{userSeq}")
    public ResponseEntity<?> countUnReadNotifications(@PathVariable("userSeq") Long userSeq) {
        Long count = notificationService.countUnReadNotifications(userSeq);
        return ResponseEntity.ok().body(count);
    }

    @Operation(summary = "읽은 알림 개수", description = "읽은 알림 개수를 리턴한다.")
    @GetMapping(value = "/noti/read/{userSeq}")
    public ResponseEntity<?> countReadNotifications(@PathVariable("userSeq") Long userSeq) {
        Long count = notificationService.countReadNotifications(userSeq);
        return ResponseEntity.ok().body(count);
    }

    @Operation(summary = "알림 전송 테스트", description = "알림 전송 테스트")
    @PostMapping(value = "/noti/sendTest")
    public ResponseEntity<?> sendNotifications(@RequestBody NotificationRequestDto dto) {
        notificationService.send(dto.getUserSeq(), NotificationType.MESSAGE,dto.getContent(), dto.getUrl());
        return ResponseEntity.ok().body("전송 완료");
    }

    @Operation(summary = "유저 알림 개별 삭제", description = "유저 알림 개별 삭제")
    @DeleteMapping(value = "/noti/del/{userSeq}/{notiId}")
    public ResponseEntity<?> deleteNotification(@PathVariable("userSeq") Long userSeq,
                                   @PathVariable("notiId") Long notiId,
                                   @RequestHeader("Authorization") String token) {
        //userSeq 일치, access토큰 유효 여부 체크
        token = token.substring(7);
        JwtState state = jwtService.checkUserSeqWithAccess(userSeq, token);
        if (state.equals(JwtState.MISMATCH_USER)) { //userSeq 불일치
            return ResponseEntity.ok().body(jwtService.mismatchUserResponse());
        } else if (state.equals(JwtState.EXPIRED_ACCESS)) { //access 만료
            return ResponseEntity.ok().body(jwtService.requiredRefreshTokenResponse());
        }
        notificationService.deleteNoti(notiId);
        return ResponseEntity.ok().body("삭제 완료");
    }

    @Operation(summary = "유저 알림 전체 삭제", description = "유저 알림 전체 삭제")
    @DeleteMapping(value = "/noti/delAll/{userSeq}")
    public ResponseEntity<?> deleteAllNotifications(@PathVariable("userSeq") Long userSeq,
                                       @RequestHeader("Authorization") String token) {
        //userSeq 일치, access토큰 유효 여부 체크
        token = token.substring(7);
        JwtState state = jwtService.checkUserSeqWithAccess(userSeq, token);
        if (state.equals(JwtState.MISMATCH_USER)) { //userSeq 불일치
            return ResponseEntity.ok().body(jwtService.mismatchUserResponse());
        } else if (state.equals(JwtState.EXPIRED_ACCESS)) { //access 만료
            return ResponseEntity.ok().body(jwtService.requiredRefreshTokenResponse());
        }
        notificationService.deleteAllNotis(userSeq);
        return ResponseEntity.ok().body("삭제 완료");
    }
}