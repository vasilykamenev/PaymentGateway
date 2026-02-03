package com.checkout.payment.gateway.model.gateway;

import com.checkout.payment.gateway.enums.PaymentStatus;
import java.io.Serializable;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentResponse implements Serializable {

  private UUID id;
  private PaymentStatus status;
  @JsonProperty("card_number_last_four")
  private String cardNumberLastFour;
  @JsonProperty("expiry_month")
  private int expiryMonth;
  @JsonProperty("expiry_year")
  private int expiryYear;
  private String currency;
  private int amount;

}
