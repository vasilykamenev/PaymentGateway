package com.checkout.payment.gateway.exception;

import com.checkout.payment.gateway.RandomUtility;
import com.checkout.payment.gateway.enums.PaymentStatus;
import com.checkout.payment.gateway.mapper.PaymentEntityMapper;
import com.checkout.payment.gateway.model.gateway.PaymentResponse;
import com.checkout.payment.gateway.repository.PaymentsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class NonValidPaymentExceptionHandlerTest {

  NonValidPaymentExceptionHandler exceptionHandler;

  @Mock
  PaymentsRepository paymentsRepository;

  @Mock
  MethodArgumentNotValidException exception;

  @BeforeEach
  void setUp() {
    exceptionHandler = new NonValidPaymentExceptionHandler(PaymentEntityMapper.INSTANCE, paymentsRepository);
  }

  @DisplayName("Processing exception MethodArgumentNotValidException")
  @Test
  void testHandleException() {
    //given
    var request = RandomUtility.generatePaymentRequest();
    when(exception.getTarget()).thenReturn(request);
    doNothing().when(paymentsRepository).add(any());
    //when
    ResponseEntity<PaymentResponse> result = exceptionHandler.handleException(exception);
    //then
    assertNotNull(result);
    assertEquals(BAD_REQUEST, result.getStatusCode());
    assertNotNull(result.getBody());
    assertInstanceOf(PaymentResponse.class, result.getBody());
    assertEquals(request.getAmount(), result.getBody().getAmount());
    assertEquals(request.getCurrency(), result.getBody().getCurrency());
    assertNotNull(result.getBody().getId());
    assertNotNull(result.getBody().getCardNumberLastFour());
    assertEquals(request.getExpiryMonth(), result.getBody().getExpiryMonth());
    assertEquals(request.getExpiryYear(), result.getBody().getExpiryYear());
    assertEquals(PaymentStatus.REJECTED, result.getBody().getStatus());
  }

}