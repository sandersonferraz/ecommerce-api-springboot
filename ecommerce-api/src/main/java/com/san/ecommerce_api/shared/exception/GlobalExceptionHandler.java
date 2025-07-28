package com.san.ecommerce_api.shared.exception;

import com.san.ecommerce_api.shared.message.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private MessageService messageService;

  private ResponseEntity<ExceptionDetails> buildResponseEntity(HttpStatus status, String titleKey,
      Exception ex, HttpServletRequest request) {
    return ResponseEntity.status(status)
        .body(ExceptionDetails.builder().title(messageService.getMessage(titleKey))
            .code(status.value()).message(ex.getMessage())
            .cause(ex.getCause() != null ? ex.getCause().getMessage() : null)
            .timestamp(LocalDateTime.now()).path(request.getRequestURI()).build());

  }

  @ExceptionHandler({com.san.ecommerce_api.shared.exception.NotFoundException.class,
      NoSuchMessageException.class})
  public ResponseEntity<ExceptionDetails> handleNotFound(RuntimeException ex,
      HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.NOT_FOUND, "not.found.title", ex, request);
  }

  @ExceptionHandler(com.san.ecommerce_api.shared.exception.HttpMessageNotReadableException.class)
  public ResponseEntity<ExceptionDetails> handleBadRequest(HttpMessageNotReadableException ex,
      HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.BAD_REQUEST, "bad.request.title", ex, request);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ExceptionDetails> handleUnauthorized(AuthenticationException ex,
      HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.UNAUTHORIZED, "unauthorized.title", ex, request);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ExceptionDetails> handleForbidden(AccessDeniedException ex,
      HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.FORBIDDEN, "forbidden.title", ex, request);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ExceptionDetails> handleMethodNotAllowed(
      HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, "method.not.allowed.title", ex,
        request);
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<ExceptionDetails> handleNotAcceptable(
      HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.NOT_ACCEPTABLE, "not.acceptable.title", ex, request);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ExceptionDetails> handleConflict(ConflictException ex,
      HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.CONFLICT, "conflict.title", ex, request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionDetails> handleUnprocessableEntity(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, "unprocessable.entity.title", ex,
        request);
  }

  @ExceptionHandler(TooManyRequestsException.class)
  public ResponseEntity<ExceptionDetails> handleTooManyRequests(TooManyRequestsException ex,
      HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.TOO_MANY_REQUESTS, "too.many.requests.title", ex,
        request);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ExceptionDetails> handleBusinessException(BusinessException ex,
      HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, "unprocessable.entity.title", ex,
        request);
  }

  @ExceptionHandler(ServiceUnavailableException.class)
  public ResponseEntity<ExceptionDetails> handleServiceUnavailable(ServiceUnavailableException ex,
      HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, "service.unavailable.title", ex,
        request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDetails> handleGenericException(Exception ex,
      HttpServletRequest request) {
    return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error.title", ex,
        request);
  }


}
