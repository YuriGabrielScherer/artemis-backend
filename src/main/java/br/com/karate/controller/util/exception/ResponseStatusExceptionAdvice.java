package br.com.karate.controller.util.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ResponseStatusExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseStatusExceptionAdviceOutput> resolveException(ResponseStatusException ex) {
        ResponseStatusExceptionAdviceOutput output = new ResponseStatusExceptionAdviceOutput(ex.getStatus(), ex.getReason());
        return ResponseEntity.status(ex.getStatus()).body(output);
    }

    @AllArgsConstructor
    public class ResponseStatusExceptionAdviceOutput {
        public HttpStatus status;
        public String message;
    }

}
