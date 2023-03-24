package com.cosmos.back.controller;

import com.cosmos.back.dto.user.UserProfileDto;
import com.cosmos.back.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Slf4j
@RestController
@RequestMapping("/api")
public class SseController {

    private final NotificationService notificationService;

    /**
     * 클라이언트로부터 알림 구독 요청을 받음
     * @AuthenticationPrincipal : 누구에게서 온 알림 구독인지 확인 가능한 어노테이션, by Spring Security
     */
    @Operation(summary = "알림 구독", description = "알림을 구독한다.")
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe(@AuthenticationPrincipal UserProfileDto user,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(user.getUserSeq(), lastEventId);
    }
}