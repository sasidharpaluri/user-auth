package dev.sasidhar.userauth.Controllers;

import dev.sasidhar.userauth.Exceptions.IncorrectCredentials;
import dev.sasidhar.userauth.Exceptions.InsufficientDetails;
import dev.sasidhar.userauth.Exceptions.UserAlreadyExists;
import dev.sasidhar.userauth.Exceptions.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {
@ExceptionHandler(UserAlreadyExists.class)
public ResponseEntity<String> userAlreadyExists(Exception exp){
return new ResponseEntity<>(exp.getMessage(), HttpStatus.CONFLICT);
}

@ExceptionHandler(InsufficientDetails.class)
public ResponseEntity<String> insufficientDetails(Exception exp){
    return new ResponseEntity<>(exp.getMessage(),HttpStatus.PARTIAL_CONTENT);
}

@ExceptionHandler(IncorrectCredentials.class)
    public ResponseEntity<String> incorrectCredentials(Exception exp){
    return new ResponseEntity<>(exp.getMessage(), HttpStatus.CONFLICT);
}

@ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> userNotFound(Exception exp){
    return new ResponseEntity<>(exp.getMessage(), HttpStatus.NOT_FOUND);
    }
}
