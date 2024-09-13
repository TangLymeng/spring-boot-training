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

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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

        // Log headers, excluding sensitive information
        Map<String, String> headers = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, request::getHeader));
        headers.remove("authorization"); // Mask sensitive data
        logger.info("Request Headers: {}", headers);

        // Log parameters
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.isEmpty()) {
            logger.info("Request Params: None");
        } else {
            logger.info("Request Params: {}", parameterMap.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + Arrays.toString(entry.getValue()))
                    .collect(Collectors.joining(", ")));
        }
    }

    @AfterReturning(pointcut = "execution(* com.example.demo.controller..*(..))", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {
        logger.info("Outgoing response for: {} {}", request.getMethod(), request.getRequestURI());

        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            logger.info("Response Status: {}", responseEntity.getStatusCode());

            // Log response body cleanly
            logger.info("Response Body: {}", responseEntity.getBody());
        } else {
            logger.info("Response: {}", result);
        }
    }
}