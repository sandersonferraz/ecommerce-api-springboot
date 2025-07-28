package com.san.ecommerce_api.shared.exception;

public class TooManyRequestsException extends BusinessException {
  public TooManyRequestsException(String message) {
    super(message);
  }
}
