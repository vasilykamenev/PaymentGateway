package com.checkout.payment.gateway.model;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class ErrorResponse implements Serializable {

  private final int status;
  private final String message;
  private String details;

  public ErrorResponse(int status, String message, String details) {
    this.status = status;
    this.message = message;
    this.details = details;
  }

  public ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

}
