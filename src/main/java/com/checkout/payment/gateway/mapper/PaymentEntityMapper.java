package com.checkout.payment.gateway.mapper;

import com.checkout.payment.gateway.model.bank.AuthorizedResponse;
import com.checkout.payment.gateway.model.bank.BankPaymentRequest;
import com.checkout.payment.gateway.model.gateway.PaymentRequest;
import com.checkout.payment.gateway.model.gateway.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.lang.NonNull;

@Mapper
public interface PaymentEntityMapper {

  PaymentEntityMapper INSTANCE = Mappers.getMapper(PaymentEntityMapper.class);

  @Mapping(target = "cardNumber", source = "cardNumber")
  @Mapping(target = "expiryDate", source = "paymentRequest", qualifiedByName = "joinMonthYear")
  BankPaymentRequest transformToBankEntity(PaymentRequest paymentRequest);

  @Named("joinMonthYear")
  default String joinMonthYear(@NonNull PaymentRequest paymentRequest) {
    return String.format("%02d/%04d", paymentRequest.getExpiryMonth(),
        paymentRequest.getExpiryYear());
  }

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "cardNumberLastFour", expression = "java(org.apache.commons.lang3.StringUtils.right(paymentRequest.getCardNumber(), 4 ))")
  @Mapping(target = "status", expression = "java(authorizedResponse.isAuthorized()?com.checkout.payment.gateway.enums.PaymentStatus.AUTHORIZED:com.checkout.payment.gateway.enums.PaymentStatus.DECLINED)")
  PaymentResponse transformToResponse(PaymentRequest paymentRequest,
      AuthorizedResponse authorizedResponse);

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "cardNumberLastFour", expression = "java(org.apache.commons.lang3.StringUtils.right(paymentRequest.getCardNumber(), 4 ))")
  @Mapping(target = "status", expression = "java(com.checkout.payment.gateway.enums.PaymentStatus.REJECTED)")
  PaymentResponse transformToRejectedResponse(PaymentRequest paymentRequest);

}
