package com.checkout.payment.gateway.mapper;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.checkout.payment.gateway.RandomUtility;
import com.checkout.payment.gateway.enums.PaymentStatus;
import com.checkout.payment.gateway.model.bank.AuthorizedResponse;
import com.checkout.payment.gateway.model.gateway.PaymentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PaymentEntityMapperTest {

  PaymentEntityMapper paymentEntityMapper = PaymentEntityMapper.INSTANCE;

  @DisplayName("Convert to MM/YYYY")
  @Test
  void t1() {
    //given
    PaymentRequest paymentRequest = RandomUtility.generatePaymentRequest();
    //when
    var result = paymentEntityMapper.joinMonthYear(paymentRequest);
    //then
    assertNotNull(result);
    assertEquals(
        format("%02d", paymentRequest.getExpiryMonth()) + "/" + paymentRequest.getExpiryYear(),
        result);
  }

  @DisplayName("Convert to MM/YYYY throw exception")
  @Test
  void t2() {

    //when
    //then
    //noinspection DataFlowIssue
    assertThrows(NullPointerException.class, () -> paymentEntityMapper.joinMonthYear(null));
  }

  @DisplayName("Transform to Bank request")
  @Test
  void t3() {
    //given
    var paymentRequest = RandomUtility.generatePaymentRequest();
    //when
    var result = paymentEntityMapper.transformToBankEntity(paymentRequest);
    //then
    assertNotNull(result);
    assertEquals(paymentRequest.getCardNumber(), result.getCardNumber());
    assertEquals(paymentRequest.getCvv(), result.getCvv());
    assertEquals(paymentRequest.getCurrency(), result.getCurrency());
    assertEquals(paymentRequest.getAmount(), result.getAmount());
    assertTrue(result.getExpiryDate().contains(paymentRequest.getExpiryMonth() + "/" + paymentRequest.getExpiryYear()));
  }

  @DisplayName("Transform to Payment response")
  @ValueSource(booleans = {true, false})
  @ParameterizedTest(name = "{index} - when authorized is {0}")
  void t4(boolean isAuthorized) {
    //given
    var paymentRequest = RandomUtility.generatePaymentRequest();
    paymentRequest.setCardNumber("1111222233334444");
    AuthorizedResponse authorizedResponse = RandomUtility.gerateAuthorizedResponse();
    authorizedResponse.setAuthorized(isAuthorized);
    var status = isAuthorized ? PaymentStatus.AUTHORIZED : PaymentStatus.DECLINED;
    //when
    var result = paymentEntityMapper.transformToResponse(paymentRequest, authorizedResponse);
    //then
    assertNotNull(result);
    assertEquals("4444", result.getCardNumberLastFour());
    assertEquals(paymentRequest.getCurrency(), result.getCurrency());
    assertEquals(paymentRequest.getAmount(), result.getAmount());
    assertEquals(paymentRequest.getExpiryMonth(), result.getExpiryMonth());
    assertEquals(paymentRequest.getExpiryYear(), result.getExpiryYear());

    assertEquals(isAuthorized, authorizedResponse.isAuthorized());
    assertEquals(status, result.getStatus());
  }
}