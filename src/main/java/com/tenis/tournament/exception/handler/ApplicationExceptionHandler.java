package com.tenis.tournament.exception.handler;

import com.tenis.tournament.exception.TournamentException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler(TournamentException.class)
  public ResponseEntity<?> handleTournamentException(TournamentException bookAPIException) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("error message", bookAPIException.getMessage());
    errorMap.put("status", HttpStatus.BAD_REQUEST.toString());
    return ResponseEntity.ok(errorMap);
  }
}


