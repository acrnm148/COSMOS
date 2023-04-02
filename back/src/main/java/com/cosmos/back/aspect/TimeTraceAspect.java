package com.cosmos.back.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAspect {
    @Around("execution(* com.cosmos.back.controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toShortString());

        try {
            return joinPoint.proceed(); // 다음 메서드로 진행
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END: " + joinPoint.toShortString() + " " + timeMs + "ms");
        }
    }
}


