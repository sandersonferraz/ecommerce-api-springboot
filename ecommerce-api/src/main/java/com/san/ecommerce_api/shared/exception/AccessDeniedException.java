package com.san.ecommerce_api.shared.exception;

public class AccessDeniedException extends BusinessException {
  public AccessDeniedException(String message) {
    super(message);
  }
}
