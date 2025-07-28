package com.san.ecommerce_api.shared.exception;

public class MethodArgumentNotValidException extends BusinessException {
  public MethodArgumentNotValidException(String message) {
    super(message);
  }
}
