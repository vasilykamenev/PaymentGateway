package com.checkout.payment.gateway.model.bank;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomUtils.insecure;
import static org.junit.jupiter.api.Assertions.*;

class BankPaymentRequestTest {

  @DisplayName("Check structure BankPaymentRequest")
  @Test
  void t1() {
    //given
    BankPaymentRequest bankPaymentRequest = new BankPaymentRequest();
    bankPaymentRequest.setCurrency("USD");
    bankPaymentRequest.setAmount(insecure().randomInt(1, 1000));
    bankPaymentRequest.setCvv(RandomStringUtils.insecure().nextNumeric(3,4));
    bankPaymentRequest.setExpiryDate("01/2026");
    bankPaymentRequest.setCardNumber(RandomStringUtils.insecure().nextNumeric(14,19));

    //then
    assertNotNull(bankPaymentRequest.getCardNumber());
    assertNotNull(bankPaymentRequest.getExpiryDate());
    assertNotNull(bankPaymentRequest.getCurrency());
    assertTrue(bankPaymentRequest.getAmount() > 0);
    assertNotNull(bankPaymentRequest.getCvv());
  }

}