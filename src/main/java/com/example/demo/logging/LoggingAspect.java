package com.example.demo.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private final HttpServletRequest request;

    public LoggingAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Before("execution(* com.example.demo.controller..*(..))")
    public void logRequest(JoinPoint joinPoint) {
        logger.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
        logger.info("Request Headers: {}", request.getHeaderNames());
        logger.info("Request Params: {}", request.getParameterMap());
    }

    @AfterReturning(pointcut = "execution(* com.example.demo.controller..*(..))", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {
        logger.info("Outgoing response for: {} {}", request.getMethod(), request.getRequestURI());

        // Log only the necessary details instead of the whole object
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            logger.info("Response Status: {}", responseEntity.getStatusCode());
            logger.info("Response Body: {}", responseEntity.getBody()); // Adjust based on what you want to log
        } else {
            logger.info("Response: {}", result);
        }
    }
}