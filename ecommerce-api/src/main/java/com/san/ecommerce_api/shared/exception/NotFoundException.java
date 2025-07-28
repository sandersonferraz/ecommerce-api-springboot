package com.san.ecommerce_api.shared.exception;

public class NotFoundException extends BusinessException {
  public NotFoundException(String message) {
    super(message);
  }
}
