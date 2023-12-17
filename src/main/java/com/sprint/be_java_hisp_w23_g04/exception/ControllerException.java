package com.sprint.be_java_hisp_w23_g04.exception;

import com.sprint.be_java_hisp_w23_g04.dto.response.SimpleMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerException {
    /*
    @ExceptionHandler(LinkInvalidPasswordException.class)
    public ResponseEntity<?> invalidPassword(LinkInvalidPasswordException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }
    */

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(UserNotFoundException e) {
        SimpleMessageDTO exceptionDto = new SimpleMessageDTO(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }

}
