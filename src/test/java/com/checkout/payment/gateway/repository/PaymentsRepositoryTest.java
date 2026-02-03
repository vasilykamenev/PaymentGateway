package com.checkout.payment.gateway.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.checkout.payment.gateway.RandomUtility;
import com.checkout.payment.gateway.enums.PaymentStatus;
import com.checkout.payment.gateway.model.gateway.PaymentResponse;
import java.util.HashMap;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class PaymentsRepositoryTest {

  private PaymentsRepository repository;
  private HashMap<UUID, PaymentResponse> reflections;

  @BeforeEach
  void setUp() {
    repository = new PaymentsRepository();
    //noinspection unchecked
    reflections = (HashMap<UUID, PaymentResponse>) ReflectionTestUtils.getField(repository,
        "payments");
  }

  @DisplayName("Add to repository")
  @Test
  void add() {
    //given
    var response = RandomUtility.generatePaymentResponse(PaymentStatus.AUTHORIZED);
    //when
    repository.add(response);
    //then
    var responseRequest = reflections.get(response.getId());
    assertNotNull(responseRequest);
    assertEquals(PaymentStatus.AUTHORIZED, responseRequest.getStatus());
    assertEquals(response.getId(), responseRequest.getId());
    assertEquals(response.getAmount(), responseRequest.getAmount());
    assertEquals(response.getExpiryYear(), responseRequest.getExpiryYear());
    assertEquals(response.getExpiryMonth(), responseRequest.getExpiryMonth());
    assertEquals(response.getCardNumberLastFour(), responseRequest.getCardNumberLastFour());
    assertEquals(response.getAmount(), responseRequest.getAmount());
    assertEquals(response.getCurrency(), responseRequest.getCurrency());
  }

  @DisplayName("Get from repository")
  @Test
  void get() {
    //given
    var response = RandomUtility.generatePaymentResponse(PaymentStatus.DECLINED);
    reflections.put(response.getId(), response);
    //when
    var responseRequest = repository.get(response.getId()).orElse(null);
    //then
    assertNotNull(responseRequest);
    assertEquals(PaymentStatus.DECLINED, responseRequest.getStatus());
    assertEquals(response.getId(), responseRequest.getId());
    assertEquals(response.getAmount(), responseRequest.getAmount());
    assertEquals(response.getExpiryYear(), responseRequest.getExpiryYear());
    assertEquals(response.getExpiryMonth(), responseRequest.getExpiryMonth());
    assertEquals(response.getCardNumberLastFour(), responseRequest.getCardNumberLastFour());
    assertEquals(response.getAmount(), responseRequest.getAmount());
    assertEquals(response.getCurrency(), responseRequest.getCurrency());
  }
}