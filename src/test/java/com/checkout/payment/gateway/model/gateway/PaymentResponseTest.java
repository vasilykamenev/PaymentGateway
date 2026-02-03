package com.checkout.payment.gateway.model.gateway;

import com.checkout.payment.gateway.enums.PaymentStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.apache.commons.lang3.RandomUtils.insecure;
import static org.junit.jupiter.api.Assertions.*;

class PaymentResponseTest {


  @DisplayName("Test structure of Payment response")
  @EnumSource(PaymentStatus.class)
  @ParameterizedTest(name = " - for status {0}")
  void t0(PaymentStatus paymentStatus) {
    //given
    var paymentResponse = new PaymentResponse();
    paymentResponse.setCardNumberLastFour(RandomStringUtils.insecure().nextNumeric(4));
    paymentResponse.setId(UUID.randomUUID());
    paymentResponse.setAmount(insecure().randomInt(1, 10000));
    paymentResponse.setCurrency("USD");
    paymentResponse.setExpiryMonth(insecure().randomInt(1, 12));
    paymentResponse.setExpiryYear(insecure().randomInt(2025, 2099));
    paymentResponse.setStatus(paymentStatus);

    //then
    assertNotNull(paymentResponse.getId());
    assertNotNull(paymentResponse.getCardNumberLastFour());
    assertTrue(paymentResponse.getAmount() > 0);
    assertNotNull(paymentResponse.getCurrency());
    assertTrue(paymentResponse.getExpiryMonth() > 0);
    assertTrue(paymentResponse.getExpiryYear() > 2024);
    assertTrue(paymentResponse.getExpiryYear() < 2100);
    assertNotNull(paymentResponse.getStatus());
    assertEquals(paymentStatus, paymentResponse.getStatus());
  }
}