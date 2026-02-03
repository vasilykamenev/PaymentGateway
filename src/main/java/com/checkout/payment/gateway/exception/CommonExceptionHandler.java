package com.checkout.payment.gateway.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.checkout.payment.gateway.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(EventProcessingException.class)
  public ResponseEntity<ErrorResponse> handleException(EventProcessingException exception) {
    logError(exception);
    var errorResponse = new ErrorResponse(NOT_FOUND.value(), "Page not found");
    return new ResponseEntity<>(errorResponse, NOT_FOUND);
  }

  @ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<ErrorResponse> handleException(HttpStatusCodeException exception) {
    logError(exception);
    var message = exception.getStatusText();
    var errorResponse = new ErrorResponse(exception.getStatusCode().value(), message);
    return new ResponseEntity<>(errorResponse, exception.getStatusCode());
  }

  private void logError(Exception exception) {
    log.error("Exception happened", exception);
  }
}
