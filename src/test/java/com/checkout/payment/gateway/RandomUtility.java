package com.checkout.payment.gateway;

import static org.apache.commons.lang3.RandomUtils.insecure;
import static org.apache.commons.lang3.RandomUtils.nextInt;

import com.checkout.payment.gateway.enums.PaymentStatus;
import com.checkout.payment.gateway.model.bank.AuthorizedResponse;
import com.checkout.payment.gateway.model.gateway.PaymentRequest;
import com.checkout.payment.gateway.model.gateway.PaymentResponse;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class RandomUtility {

  private RandomUtility() {
  }

  public static PaymentRequest generatePaymentRequest() {
    var request = new PaymentRequest();
    request.setCvv(RandomStringUtils.insecure().nextNumeric(3, 4));
    request.setAmount(insecure().randomInt(1, 2000));
    request.setCurrency("EUR");
    request.setCardNumber(RandomStringUtils.insecure().nextNumeric(14, 19));
    request.setExpiryYear(insecure().randomInt(2025, 2050));
    request.setExpiryMonth(insecure().randomInt(1, 12));
    return request;
  }

  public static PaymentResponse generatePaymentResponse(PaymentStatus status) {
    var response = new PaymentResponse();
    response.setId(UUID.randomUUID());
    response.setAmount(insecure().randomInt(1, 2000));
    response.setCurrency("EUR");
    response.setCardNumberLastFour(RandomStringUtils.insecure().nextNumeric(4));
    response.setExpiryYear(insecure().randomInt(2025, 2050));
    response.setExpiryMonth(insecure().randomInt(1, 12));
    response.setStatus(status);
    return response;
  }

  public static AuthorizedResponse gerateAuthorizedResponse() {
    var response = new AuthorizedResponse();
    response.setAuthorizationCode(RandomStringUtils.insecure().nextAlphabetic(16));
    response.setAuthorized(RandomUtils.insecure().randomBoolean());
    return response;
  }
}
