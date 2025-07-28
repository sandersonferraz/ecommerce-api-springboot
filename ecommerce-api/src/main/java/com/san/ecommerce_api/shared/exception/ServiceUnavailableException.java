package com.san.ecommerce_api.shared.exception;

public class ServiceUnavailableException extends BusinessException {
  public ServiceUnavailableException(String message) {
    super(message);
  }
}
