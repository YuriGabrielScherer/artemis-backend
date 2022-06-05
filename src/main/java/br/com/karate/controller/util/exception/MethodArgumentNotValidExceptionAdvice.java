package br.com.karate.controller.util.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MethodArgumentNotValidExceptionAdvice {

    @AllArgsConstructor
    public class MethodArgumentNotValidOutput {
        public String field;
        public String message;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<MethodArgumentNotValidOutput>> resolveException(MethodArgumentNotValidException ex) {

        List<MethodArgumentNotValidOutput> output = ex.getAllErrors() //
                .stream() //
                .map(error -> new MethodArgumentNotValidOutput(error.getCode(), error.getDefaultMessage())) //
                .collect(Collectors.toList());

        return ResponseEntity.badRequest() //
                             .body(output);
    }

}
