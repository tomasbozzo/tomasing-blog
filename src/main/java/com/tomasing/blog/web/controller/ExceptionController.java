package com.tomasing.blog.web.controller;

import com.tomasing.blog.web.model.ExceptionResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResource> handleException(Exception exception) {
        ResponseStatus annotation = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
        if (annotation != null) {
            log.debug(exception.getClass().getCanonicalName() + ": " + annotation.reason());
            return toResponseEntity(exception, annotation);
        } else {
            log.error("Unhandled exception", exception);
            return toResponseEntity(exception);
        }
    }

    private ResponseEntity<ExceptionResource> toResponseEntity(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResource.builder()
                        .message(exception.getMessage())
                        .build());
    }

    private ResponseEntity<ExceptionResource> toResponseEntity(Exception exception, ResponseStatus annotation) {
        return ResponseEntity
                .status(annotation.code())
                .body(ExceptionResource.builder()
                        .message(getMessage(exception, annotation))
                        .build());
    }

    private String getMessage(Exception exception, ResponseStatus annotation) {
        return Optional.ofNullable(exception)
                .map(Exception::getMessage)
                .orElseGet(annotation::reason);
    }
}
