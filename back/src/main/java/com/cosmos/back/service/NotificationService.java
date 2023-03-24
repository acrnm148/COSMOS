package com.cosmos.back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

import static com.cosmos.back.controller.SseController.sseEmitters;

@RequiredArgsConstructor
@Service
public class NotificationService {

//    private final MemoRepository memoRepository;
//
//    public void notifyAddCommentEvent(Long memoId) {
//        // 댓글에 대한 처리 후 해당 댓글이 달린 게시글의 pk값으로 게시글을 조회
//        Memo memo = memoRepository.findById(memoId).orElseThrow(
//                () -> new IllegalArgumentException("찾을 수 없는 메모입니다.")
//        );
//        Long userId = memo.getUser().getId();
//
//        if (sseEmitters.containsKey(userId)) {
//            SseEmitter sseEmitter = sseEmitters.get(userId);
//            try {
//                sseEmitter.send(SseEmitter.event().name("addComment").data("댓글이 달렸습니다!!!!!"));
//            } catch (Exception e) {
//                sseEmitters.remove(userId);
//            }
//        }
//    }
}
