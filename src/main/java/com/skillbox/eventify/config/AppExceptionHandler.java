package com.skillbox.eventify.config;

import com.skillbox.eventify.exception.BusinessException;
import com.skillbox.eventify.model.ErrorResponse.LevelEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.skillbox.eventify.model.ErrorResponse;

@Log4j2
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception ex) {
        log.error("handle exception", ex);
        if (ex instanceof BusinessException businessException) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .level(LevelEnum.fromValue(businessException.getLevel()))
                    .message(ex.getMessage())
                    .build(), businessException.getResponseCode());
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .level(LevelEnum.ERROR)
                .message(ex.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}