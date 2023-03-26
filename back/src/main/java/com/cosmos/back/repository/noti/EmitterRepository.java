package com.cosmos.back.repository.noti;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void saveEventCache(String emitterId, Object event);
    Map<String, SseEmitter> findAllEmitterStartWithByUserSeq(String userSeq);
    Map<String, Object> findAllEventCacheStartWithByUserSeq(String userSeq);
    void deleteById(String id);
    void deleteAllEmitterStartWithId(String memberId);
    void deleteAllEventCacheStartWithId(String memberId);
}
