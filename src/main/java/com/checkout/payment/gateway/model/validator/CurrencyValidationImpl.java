package com.checkout.payment.gateway.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Currency;

public class CurrencyValidationImpl implements ConstraintValidator<CurrencyValidation, String> {

  @Override
  public boolean isValid(String currency, ConstraintValidatorContext constraintValidatorContext) {
    try {
      Currency.getInstance(currency);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
