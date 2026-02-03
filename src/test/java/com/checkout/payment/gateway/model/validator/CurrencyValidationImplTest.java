package com.checkout.payment.gateway.model.validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyValidationImplTest {

  private CurrencyValidationImpl currencyValidation;

  @BeforeEach
  void setUp() {
    currencyValidation =  new CurrencyValidationImpl();
  }

  @AfterEach
  void tearDown() {
  }

  @DisplayName("Currency is valid")
  @ValueSource(strings = {"USD", "EUR", "ITL"})
  @ParameterizedTest(name = "{index} - test value {0}")
  void t1(String currency) {
    //when
    var result = currencyValidation.isValid(currency, null);
    //then
    assertTrue(result);
  }

  @DisplayName("Currency not valid")
  @ValueSource(strings = {"XZW", "EU1", "usd"})
  @ParameterizedTest(name = "{index} - negative value is {0}")
  void t2(String currency) {
    //when
    var result = currencyValidation.isValid(currency, null);
    //then
    assertFalse(result);
  }
}