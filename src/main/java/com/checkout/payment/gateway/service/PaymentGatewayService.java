package com.checkout.payment.gateway.service;

import com.checkout.payment.gateway.exception.EventProcessingException;
import com.checkout.payment.gateway.mapper.PaymentEntityMapper;
import com.checkout.payment.gateway.model.bank.AuthorizedResponse;
import com.checkout.payment.gateway.model.gateway.PaymentRequest;
import com.checkout.payment.gateway.model.gateway.PaymentResponse;
import com.checkout.payment.gateway.repository.PaymentsRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentGatewayService {

  private final PaymentsRepository paymentsRepository;
  private final RestTemplate restTemplate;
  private final PaymentEntityMapper paymentEntityMapper;

  @Value("${acquiring-bank.url}")
  private String acquiringBankUrl;

  public PaymentResponse getPaymentById(UUID id) {
    log.debug("Requesting access to to payment with ID {}", id);
    return paymentsRepository.get(id).orElseThrow(() -> new EventProcessingException("Invalid ID"));
  }

  public PaymentResponse processPayment(PaymentRequest paymentRequest) {
    var bankPaymentRequest = paymentEntityMapper.transformToBankEntity(paymentRequest);
    var bankResponse = restTemplate.postForObject(acquiringBankUrl, bankPaymentRequest,
        AuthorizedResponse.class);
    var response = paymentEntityMapper.transformToResponse(paymentRequest, bankResponse);
    paymentsRepository.add(response);
    return response;
  }
}
