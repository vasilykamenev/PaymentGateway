package com.checkout.payment.gateway.controller;

import com.checkout.payment.gateway.model.gateway.PaymentRequest;
import com.checkout.payment.gateway.model.gateway.PaymentResponse;
import com.checkout.payment.gateway.service.PaymentGatewayService;
import java.util.UUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("api")
public class PaymentGatewayController {

  private final PaymentGatewayService paymentGatewayService;

  @GetMapping("/payment/{id}")
  public ResponseEntity<PaymentResponse> getPostPaymentEventById(@PathVariable UUID id) {
    return new ResponseEntity<>(paymentGatewayService.getPaymentById(id), HttpStatus.OK);
  }

  @PostMapping(value = "/payment")
  public ResponseEntity<PaymentResponse> sendPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
    return new ResponseEntity<>(paymentGatewayService.processPayment(paymentRequest), HttpStatus.OK);
  }
}
