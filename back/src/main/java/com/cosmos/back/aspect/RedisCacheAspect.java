package com.cosmos.back.aspect;

import com.cosmos.back.annotation.RedisCached;
import com.cosmos.back.annotation.RedisCachedKeyParam;
import com.cosmos.back.annotation.RedisEvict;
import com.cosmos.back.config.RedisDB;
import com.cosmos.back.model.RedisCacheParameterMethodInfo;
import com.google.common.base.Joiner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

@Component
@Aspect
public class RedisCacheAspect {
    private static Map<String, RedisCacheParameterMethodInfo> cacheParameterMethodInfoMap = new HashMap<>();

    @Autowired
    private RedisDB redisDB;

    @Around(value = "execution(* *(..)) && @annotation(redisCached)")
    public Object redisCached(ProceedingJoinPoint joinPoint, RedisCached redisCached) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Class returnType = methodSignature.getReturnType();

        String key = redisCached.key();
        int expire = redisCached.expire();
        boolean replace = redisCached.replace();

        // joinPoint의 매개변수들을 모두 가져온다.
        List<String> parameterKeyList = new ArrayList<>();
        Object[] args = joinPoint.getArgs();

        RedisCacheParameterMethodInfo methodInfo = cacheParameterMethodInfoMap.get(key);

        if (methodInfo != null) {
            List<RedisCacheParameterMethodInfo.IndexInfo> indexInfoList = methodInfo.getIndexInfoList();
            indexInfoList.forEach(info ->
                    parameterKeyList.add(makeCacheKey(info.getAnnotation(), args[info.getIndex()].toString())));
        } else {
            methodInfo = new RedisCacheParameterMethodInfo();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation instanceof RedisCachedKeyParam) {
                        RedisCachedKeyParam keyParam = (RedisCachedKeyParam) annotation;
                        parameterKeyList.add(makeCacheKey(keyParam, args[i].toString()));
                        methodInfo.addInfo(keyParam, i);
                    }
                }
            }

            cacheParameterMethodInfoMap.put(key, methodInfo);
        }

        // make cache key
        StringBuilder cacheKeyBuilder = new StringBuilder()
                .append(key).append("/");

        if (!CollectionUtils.isEmpty(parameterKeyList)) {
            cacheKeyBuilder.append(Joiner.on(",").join(parameterKeyList));
        }
        final String cacheKey = cacheKeyBuilder.toString();

        try {
            Object result;
            if (!replace) {
                Long ttl = redisDB.ttl(cacheKey);
                if (ttl > 0) {

                    result = redisDB.get(cacheKey, returnType);
                    return result;
                }
            }

            result = joinPoint.proceed();
            redisDB.set(cacheKey, result, returnType);
            redisDB.expire(cacheKey, expire);

            return result;
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    private String makeCacheKey(RedisCachedKeyParam keyParam, String value) {
        return String.format("%s=%s", keyParam.key(), value);
    }

    @Around(value = "execution(* *(..)) && @annotation(redisEvict)")
    public Object redisEvict(ProceedingJoinPoint joinPoint, RedisEvict redisEvict) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Class returnType = methodSignature.getReturnType();

        String key = redisEvict.key();

        // joinPoint의 매개변수들을 모두 가져온다.
        List<String> parameterKeyList = new ArrayList<>();
        Object[] args = joinPoint.getArgs();

        RedisCacheParameterMethodInfo methodInfo = cacheParameterMethodInfoMap.get(key);

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof RedisCachedKeyParam) {
                    RedisCachedKeyParam keyParam = (RedisCachedKeyParam) annotation;
                    parameterKeyList.add(makeCacheKey(keyParam, args[i].toString()));
                }
            }
        }

        // make cache key
        StringBuilder cacheKeyBuilder = new StringBuilder()
                .append(key).append("/");

        if (!CollectionUtils.isEmpty(parameterKeyList)) {
            cacheKeyBuilder.append(Joiner.on(",").join(parameterKeyList));
        }
        final String cacheKey = cacheKeyBuilder.toString();

        try {
            Long ttl = redisDB.ttl(cacheKey);
            if (ttl > 0) {
                redisDB.del(cacheKey);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        Object result = joinPoint.proceed();
        return result;
    }
}