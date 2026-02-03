package com.checkout.payment.gateway.exception;

import com.checkout.payment.gateway.mapper.PaymentEntityMapper;
import com.checkout.payment.gateway.model.gateway.PaymentRequest;
import com.checkout.payment.gateway.model.gateway.PaymentResponse;
import com.checkout.payment.gateway.repository.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class NonValidPaymentExceptionHandler {

  private final PaymentEntityMapper paymentEntityMapper;

  private final PaymentsRepository paymentsRepository;


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<PaymentResponse> handleException(MethodArgumentNotValidException exception) {
    var target = (PaymentRequest) exception.getTarget();
    var errorDetails = exception.getAllErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining("; "));
    log.debug(errorDetails);
    var paymentResponse = paymentEntityMapper.transformToRejectedResponse(target);
    paymentsRepository.add(paymentResponse);
    return new ResponseEntity<>(paymentResponse, BAD_REQUEST);
  }

}
