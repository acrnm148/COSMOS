package com.cosmos.back.repository.noti;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Repository
public interface EmitterRepository {
    public SseEmitter save(String emitterId, SseEmitter sseEmitter);
    public void saveEventCache(String emitterId, Object event);
    public Map<String, SseEmitter> findAllEmitterStartWithByUserSeq(String userSeq);
    public Map<String, Object> findAllEventCacheStartWithByUserSeq(String userSeq);
    public void deleteById(String id);
    public void deleteAllEmitterStartWithId(String memberId);
    public void deleteAllEventCacheStartWithId(String memberId);
}
