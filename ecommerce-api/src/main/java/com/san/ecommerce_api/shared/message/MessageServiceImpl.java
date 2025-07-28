package com.san.ecommerce_api.shared.message;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageSource messageSource;

  @Override
  public String getMessage(String code, Object... args) {
    return messageSource.getMessage(code, args, Locale.getDefault());
  }
}
