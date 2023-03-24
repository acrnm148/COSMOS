package com.cosmos.back.controller;

import com.cosmos.back.auth.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@RestController
public class SseController {

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final JwtService jwtService;

    @CrossOrigin
    @GetMapping(value = "/sub", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@RequestHeader(value = "Authorization") String token) {

        // 토큰에서 user의 pk값 파싱
        Long userSeq = jwtService.getUserSeq(token);

        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            // 연결!!
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // user의 pk값을 key값으로 해서 SseEmitter를 저장
        sseEmitters.put(userSeq, sseEmitter);

        sseEmitter.onCompletion(() -> sseEmitters.remove(userSeq));
        sseEmitter.onTimeout(() -> sseEmitters.remove(userSeq));
        sseEmitter.onError((e) -> sseEmitters.remove(userSeq));

        return sseEmitter;
    }

//    @PostMapping("/memo/{id}/comment")
//    public ResponseEntity addComment(
//            @PathVariable Long id,
//            @RequestBody CommentDto commentDto) {
//        Memo memo = memoService.addComment(id, userDetails.getUser(), commentDto);
//
//        // 알림 이벤트 발행 메서드 호출
//        notificationService.notifyAddCommentEvent(memo);
//
//        return ResponseEntity.ok("ok");
//    }
}