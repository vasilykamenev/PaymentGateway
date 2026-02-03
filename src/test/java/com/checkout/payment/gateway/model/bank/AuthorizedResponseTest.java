package com.checkout.payment.gateway.model.bank;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizedResponseTest {

  @DisplayName("Check structure of AuthorizedResponse")
  @Test
  void t1() {
    //given
    AuthorizedResponse authorizedResponse = new AuthorizedResponse();
    authorizedResponse.setAuthorized(true);
    authorizedResponse.setAuthorizationCode(RandomStringUtils.insecure().nextNumeric((16)));

    //then
    assertTrue(authorizedResponse.isAuthorized());
    assertNotNull(authorizedResponse.getAuthorizationCode());
  }

}