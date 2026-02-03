package com.checkout.payment.gateway.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.checkout.payment.gateway.RandomUtility;
import com.checkout.payment.gateway.enums.PaymentStatus;
import com.checkout.payment.gateway.model.gateway.PaymentResponse;
import com.checkout.payment.gateway.repository.PaymentsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
class PaymentGatewayControllerITest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  PaymentsRepository paymentsRepository;

  @DisplayName("Get payment info from repository when exist")
  @Test
  void t1() throws Exception {
    //given
    PaymentResponse payment = RandomUtility.generatePaymentResponse(PaymentStatus.AUTHORIZED);
    paymentsRepository.add(payment);

    //when
    mvc.perform(MockMvcRequestBuilders.get("/payment/" + payment.getId()))
        //then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", PaymentResponse.class).value(payment.getStatus().getName()))
        .andExpect(jsonPath("$.expiry_month").value(payment.getExpiryMonth()))
        .andExpect(jsonPath("$.expiry_year").value(payment.getExpiryYear()))
        .andExpect(jsonPath("$.currency").value(payment.getCurrency()))
        .andExpect(jsonPath("$.card_number_last_four").value(payment.getCardNumberLastFour()))
        .andExpect(jsonPath("$.amount").value(payment.getAmount()));
  }

  @DisplayName("Try to get payment when does not exist in repo")
  @Test
  void t2() throws Exception {
    //when
    mvc.perform(MockMvcRequestBuilders.get("/payment/" + UUID.randomUUID()))
        //then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Page not found"));
  }

  @DisplayName("Payment request :: when Authorized")
  @ValueSource(strings = {"1", "3", "5", "7", "9"})
  @ParameterizedTest(name = "{index} - for curd number ends on ...{0}")
  void t3(String lastDigit) throws Exception {
    //given
    var payment = RandomUtility.generatePaymentRequest();
    payment.setCardNumber(payment.getCardNumber() + lastDigit);

    //when
    mvc.perform(
            MockMvcRequestBuilders
                .post("/payment").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(payment)))
        //then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").value(PaymentStatus.AUTHORIZED.getName()))
        .andExpect(jsonPath("$.expiry_month").value(payment.getExpiryMonth()))
        .andExpect(jsonPath("$.expiry_year").value(payment.getExpiryYear()))
        .andExpect(jsonPath("$.currency").value(payment.getCurrency()))
        .andExpect(jsonPath("$.amount").value(payment.getAmount()));
  }

  @DisplayName("Payment request :: when Declined")
  @ValueSource(strings = {"2", "4", "6", "8"})
  @ParameterizedTest(name = "{index} - for curd number ends on ...{0}")
  void t4(String lastDigit) throws Exception {
    //given
    var payment = RandomUtility.generatePaymentRequest();
    payment.setCardNumber(payment.getCardNumber() + lastDigit);

    //when
    mvc.perform(
            MockMvcRequestBuilders
                .post("/payment").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(payment)))
        //then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").value(PaymentStatus.DECLINED.getName()))
        .andExpect(jsonPath("$.expiry_month").value(payment.getExpiryMonth()))
        .andExpect(jsonPath("$.expiry_year").value(payment.getExpiryYear()))
        .andExpect(jsonPath("$.currency").value(payment.getCurrency()))
        .andExpect(jsonPath("$.amount").value(payment.getAmount()));
  }

  @DisplayName("Payment request :: when internal error")
  @Test
  void t5() throws Exception {
    //given
    var payment = RandomUtility.generatePaymentRequest();
    payment.setCardNumber("123456789");
    payment.setCvv("22");

    //when
    mvc.perform(
            MockMvcRequestBuilders
                .post("/payment").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(payment)))
        //then
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").value(PaymentStatus.REJECTED.getName()))
        .andExpect(jsonPath("$.expiry_month").value(payment.getExpiryMonth()))
        .andExpect(jsonPath("$.expiry_year").value(payment.getExpiryYear()))
        .andExpect(jsonPath("$.currency").value(payment.getCurrency()))
        .andExpect(jsonPath("$.amount").value(payment.getAmount()));
  }

  @DisplayName("Payment request :: when payload has error")
  @Test
  void t6() throws Exception {
    //given
    var payment = RandomUtility.generatePaymentRequest();
    payment.setCardNumber(payment.getCardNumber() + "0");

    //when
    mvc.perform(
            MockMvcRequestBuilders
                .post("/payment").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(payment)))
        //then
        .andExpect(status().is5xxServerError())
        .andExpect(jsonPath("$.status").value(HttpStatus.SERVICE_UNAVAILABLE.value()))
        .andExpect(jsonPath("$.message").isNotEmpty());
  }

}
