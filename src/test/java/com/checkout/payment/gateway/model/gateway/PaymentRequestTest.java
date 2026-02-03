package com.checkout.payment.gateway.model.gateway;

import static com.checkout.payment.gateway.RandomUtility.generatePaymentRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PaymentRequestTest {

  private Validator validator;
  private ValidatorFactory factory;

  @BeforeEach
  void setUp() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @AfterEach
  void tearDown() {
    factory.close();
  }

  @DisplayName("Check Payment structure")
  @Test
  void t1() {
    //given
    PaymentRequest request = generatePaymentRequest();

    //then
    assertNotNull(request.getCardNumber());
    assertNotNull(request.getCvv());
    assertNotNull(request.getCurrency());
    assertTrue(request.getAmount() > 0);
    assertTrue(request.getExpiryYear() > 2024);
    assertTrue(request.getExpiryYear() < 2050);
    assertTrue(request.getExpiryMonth() >= 1);
    assertTrue(request.getExpiryMonth() <= 12);
  }

  @DisplayName("Validate Payment entity :: error of wrong Card number value")
  @Test
  void t2() {
    //given
    PaymentRequest request = generatePaymentRequest();
    request.setCardNumber(RandomStringUtils.insecure().nextAlphabetic(6));
    //when
    var violations = validator.validate(request);
    //then
    assertThat(violations).extracting(ConstraintViolation::getMessage)
        .hasSize(1)
        .containsExactlyInAnyOrder("Card number not valid");

  }

  @ValueSource(ints = {0, 122})
  @DisplayName("Validate Payment entity :: error of wrong Month value")
  @ParameterizedTest(name = "{index} - for month value {0}")
  void t3(int month) {
    //given
    PaymentRequest request = generatePaymentRequest();
    request.setExpiryMonth(month);
    //when
    var violations = validator.validate(request);
    //then
    assertThat(violations)
        .extracting(ConstraintViolation::getMessage)
        .hasSize(1)
        .containsAnyOf("Month value can't be less then 1", "Month value can't be more then 12");

  }

  @ValueSource(ints = {1900, 2055})
  @DisplayName("Validate Payment entity :: error of wrong Year value")
  @ParameterizedTest(name = "{index} - for year value {0}")
  void t4(int value) {
    //given
    PaymentRequest request = generatePaymentRequest();
    request.setExpiryYear(value);
    //when
    var violations = validator.validate(request);
    //then
    assertThat(violations)
        .extracting(ConstraintViolation::getMessage)
        .hasSize(1)
        .containsAnyOf("Year value can't be less then 2025", "Year value can't be more then 2050");
  }

  @ValueSource(ints = {-100, 0})
  @DisplayName("Validate Payment entity :: error of wrong Amount value")
  @ParameterizedTest(name = "{index} - for amount value {0}")
  void t5(int value) {
    //given
    PaymentRequest request = generatePaymentRequest();
    request.setAmount(value);
    //when
    var violations = validator.validate(request);
    //then
    assertThat(violations)
        .extracting(ConstraintViolation::getMessage)
        .hasSize(1)
        .containsAnyOf("Amount can't be less then 1");
  }

  @ValueSource(strings = {"11", "abc", "14567"})
  @DisplayName("Validate Payment entity :: error of wrong CVV number value")
  @ParameterizedTest(name = "{index} - for CVV value {0}")
  void t6(String value) {
    //given
    PaymentRequest request = generatePaymentRequest();
    request.setCvv(value);
    //when
    var violations = validator.validate(request);
    //then
    assertThat(violations).extracting(ConstraintViolation::getMessage)
        .hasSize(1)
        .containsExactlyInAnyOrder("CVV value is wrong");
  }

  @ValueSource(strings = {"EU", "abc", "USD1"})
  @DisplayName("Validate Payment entity :: error of wrong Currency number value")
  @ParameterizedTest(name = "{index} - for Currency value {0}")
  void t7(String value) {
    //given
    PaymentRequest request = generatePaymentRequest();
    request.setCurrency(value);
    //when
    var violations = validator.validate(request);
    //then
    assertThat(violations).extracting(ConstraintViolation::getMessage)
        .hasSize(1)
        .containsExactlyInAnyOrder("Currency value is wrong");
  }

}