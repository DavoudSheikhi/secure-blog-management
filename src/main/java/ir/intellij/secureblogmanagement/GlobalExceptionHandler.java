package ir.intellij.secureblogmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AccessDenied.class)
    public ResponseEntity<String> testException(AccessDenied accessDenied){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessDenied.getMessage());
    }
}
