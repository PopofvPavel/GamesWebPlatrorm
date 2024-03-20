package com.example.webprojectgames.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Класс для логгирования методов контроллеров и сервисов
 */
@Component
@Aspect
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.webprojectgames.controller.GamesController.*(..))")
    private void controllerMethods() {}

    @Pointcut("execution(* com.example.webprojectgames.services.*.*(..))")
    private void serviceMethods() {}

    @Before("controllerMethods()")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        logger.info("Calling controller method: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterControllerMethod(JoinPoint joinPoint, Object result) {
        logger.info("Finished calling controller method: " + joinPoint.getSignature().toShortString());
    }

    @Before("serviceMethods()")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        logger.info("Calling service method: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterServiceMethod(JoinPoint joinPoint, Object result) {
        logger.info("Finished calling service method: " + joinPoint.getSignature().toShortString());
    }

}
