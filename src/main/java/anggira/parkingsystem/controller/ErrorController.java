package anggira.parkingsystem.controller;

import anggira.parkingsystem.model.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<String>> constraintViolationException (ConstraintViolationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.<String>builder().errors(exception.getMessage()).statusCode(HttpStatus.BAD_REQUEST.value()).build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponse<String>> responseStatusException (ResponseStatusException exception){
        return ResponseEntity.status(exception.getStatus())
                .body(CommonResponse.<String>builder().errors(exception.getMessage()).statusCode(exception.getStatus().value()).build());

    } @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<CommonResponse<String>> sQLSyntaxErrorException (SQLSyntaxErrorException exception){
        return ResponseEntity.status(500)
                .body(CommonResponse.<String>builder().errors(exception.getMessage()).build());
    }

}
