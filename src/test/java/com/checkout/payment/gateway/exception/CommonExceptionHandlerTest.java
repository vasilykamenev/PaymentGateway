package com.checkout.payment.gateway.exception;

import com.checkout.payment.gateway.model.ErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class CommonExceptionHandlerTest {

  CommonExceptionHandler commonExceptionHandler = new CommonExceptionHandler();

  @DisplayName("Processing exception EventProcessingException")
  @Test
  void t1() {
    //given
    EventProcessingException exception = new EventProcessingException("Error message 1");
    //when
    ResponseEntity<ErrorResponse> result = commonExceptionHandler.handleException(exception);
    //then
    assertNotNull(result);
    assertEquals(NOT_FOUND, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(NOT_FOUND.value(), result.getBody().getStatus());
    assertEquals("Page not found", result.getBody().getMessage());
  }

}