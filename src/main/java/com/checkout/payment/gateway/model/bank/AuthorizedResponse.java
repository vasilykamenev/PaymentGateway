package com.checkout.payment.gateway.model.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizedResponse {

  private boolean authorized;
  @JsonProperty("authorization_code")
  private String authorizationCode;

}
