package com.san.ecommerce_api.shared.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ExceptionDetails {

  protected String title;
  protected int code;
  protected String message;
  protected String details;
  protected LocalDateTime timestamp;
  protected String path;
  protected String cause;

}
