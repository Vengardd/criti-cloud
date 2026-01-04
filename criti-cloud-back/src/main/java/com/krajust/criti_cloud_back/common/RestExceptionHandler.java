package com.krajust.criti_cloud_back.common;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e) {
        log.error("Error occurred", e);
        log.error(ExceptionUtils.getStackTrace(e));
        return e.getMessage();
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFound(NoResourceFoundException ex, HttpServletRequest req) {
        if (req.getRequestURI().equals("/favicon.ico")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }


}
