package com.checkout.payment.gateway.model.gateway;

import com.checkout.payment.gateway.model.validator.CurrencyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PaymentRequest implements Serializable {

  @Pattern(regexp = "^\\d{14,19}$", message = "Card number not valid")
  @JsonProperty("card_number")
  private String cardNumber;
  @Min(value = 1, message = "Month value can't be less then 1")
  @Max(value = 12, message = "Month value can't be more then 12")
  @JsonProperty("expiry_month")
  private int expiryMonth;
  @Min(value = 2025, message = "Year value can't be less then 2025")
  @Max(value = 2050, message = "Year value can't be more then 2050")
  @JsonProperty("expiry_year")
  private int expiryYear;
  @CurrencyValidation(message = "Currency value is wrong")
  private String currency;
  @Min(value = 1 , message = "Amount can't be less then 1")
  private int amount;
  @Pattern(regexp = "\\d{3,4}", message = "CVV value is wrong")
  private String cvv;

}
