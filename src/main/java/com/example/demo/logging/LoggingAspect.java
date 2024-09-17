package com.example.demo.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final HttpServletRequest request;

    public LoggingAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Around("execution(* com.example.demo.controller..*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());

        // Log headers
        logHeaders();

        // Log the user
        logger.info("Incoming request: {} {} by user: {}", request.getMethod(), request.getRequestURI(), request.getRemoteUser());

        // Log method arguments (request body for POST/PUT)
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            logRequestBody(joinPoint);
        }

        Object result = joinPoint.proceed();

        // Log the response
        logResponse(result);

        return result;
    }

    private void logHeaders() {
        logger.info("Request Headers: {}", Collections.list(request.getHeaderNames())
                .stream()
                .filter(h -> !"authorization".equalsIgnoreCase(h))
                .collect(Collectors.toMap(h -> h, request::getHeader)));
    }

    private void logRequestBody(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null) {
                logger.info("Request Body: {}", arg.toString());
            }
        }
    }

    private void logResponse(Object result) {
        logger.info("Outgoing response for: {} {}", request.getMethod(), request.getRequestURI());

        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            logger.info("Response Status: {}", responseEntity.getStatusCode());
            logger.info("Response Body: {}", responseEntity.getBody());
        } else {
            logger.info("Response: {}", result);
        }
    }
}
