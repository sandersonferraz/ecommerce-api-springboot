package com.san.ecommerce_api.shared.exception;

public class HttpRequestMethodNotSupportedException extends BusinessException {
  public HttpRequestMethodNotSupportedException(String message) {
    super(message);
  }
}
