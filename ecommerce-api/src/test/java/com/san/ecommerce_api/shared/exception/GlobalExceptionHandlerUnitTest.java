package com.san.ecommerce_api.shared.exception;

import com.san.ecommerce_api.shared.message.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.ServiceUnavailableException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GlobalExceptionHandler Unit Tests")
class GlobalExceptionHandlerUnitTest {

  @InjectMocks
  private GlobalExceptionHandler exceptionHandler;

  @Mock
  private MessageService messageService;

  private HttpServletRequest mockRequest() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/mocked/uri");
    return request;
  }

  private void assertDetails(ResponseEntity<?> response, HttpStatus expectedStatus, String expectedTitle) {
    assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
    ExceptionDetails details = (ExceptionDetails) response.getBody();
    assertThat(details).isNotNull();
    assertThat(details.getTitle()).isEqualTo(expectedTitle);
  }

  @Test
  @DisplayName("Should handle HttpMessageNotReadableException as 400 Bad Request")
  void shouldHandleHttpMessageNotReadableExceptionAsBadRequest() {
    HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Malformed JSON");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("bad.request.title")).thenReturn("Bad Request");

    ResponseEntity<?> response = exceptionHandler.handleBadRequest(ex, request);

    assertDetails(response, HttpStatus.BAD_REQUEST, "Bad Request");
  }

  @Test
  @DisplayName("Should handle AccessDeniedException as 403 Forbidden")
  void shouldHandleAccessDeniedExceptionAsForbidden() {
    AccessDeniedException ex = new AccessDeniedException("Access denied");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("forbidden.title")).thenReturn("Forbidden");

    ResponseEntity<?> response = exceptionHandler.handleForbidden(ex, request);

    assertDetails(response, HttpStatus.FORBIDDEN, "Forbidden");
  }

  @Test
  @DisplayName("Should handle NotFoundException as 404 Not Found")
  void shouldHandleNotFoundExceptionAsNotFound() {
    NotFoundException ex = new NotFoundException("Resource not found");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("not.found.title")).thenReturn("Not Found");

    ResponseEntity<?> response = exceptionHandler.handleNotFound(ex, request);

    assertDetails(response, HttpStatus.NOT_FOUND, "Not Found");
  }

  @Test
  @DisplayName("Should handle HttpRequestMethodNotSupportedException as 405 Method Not Allowed")
  void shouldHandleHttpRequestMethodNotSupportedExceptionAsMethodNotAllowed() {
    HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("method.not.allowed.title")).thenReturn("Method Not Allowed");

    ResponseEntity<?> response = exceptionHandler.handleMethodNotAllowed(ex, request);

    assertDetails(response, HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed");
  }

  @Test
  @DisplayName("Should handle HttpMediaTypeNotAcceptableException as 406 Not Acceptable")
  void shouldHandleHttpMediaTypeNotAcceptableExceptionAsNotAcceptable() {
    HttpMediaTypeNotAcceptableException ex = new HttpMediaTypeNotAcceptableException("Unsupported format");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("not.acceptable.title")).thenReturn("Not Acceptable");

    ResponseEntity<?> response = exceptionHandler.handleNotAcceptable(ex, request);

    assertDetails(response, HttpStatus.NOT_ACCEPTABLE, "Not Acceptable");
  }

  @Test
  @DisplayName("Should handle ConflictException as 409 Conflict")
  void shouldHandleConflictExceptionAsConflict() {
    ConflictException ex = new ConflictException("Conflict error");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("conflict.title")).thenReturn("Conflict");

    ResponseEntity<?> response = exceptionHandler.handleConflict(ex, request);

    assertDetails(response, HttpStatus.CONFLICT, "Conflict");
  }

  @Test
  @DisplayName("Should handle MethodArgumentNotValidException as 422 Unprocessable Entity")
  void shouldHandleMethodArgumentNotValidExceptionAsUnprocessableEntity() {
    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("unprocessable.entity.title")).thenReturn("Unprocessable Entity");

    ResponseEntity<?> response = exceptionHandler.handleUnprocessableEntity(ex, request);

    assertDetails(response, HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity");
  }

  @Test
  @DisplayName("Should handle TooManyRequestsException as 429 Too Many Requests")
  void shouldHandleTooManyRequestsExceptionAsTooManyRequests() {
    TooManyRequestsException ex = new TooManyRequestsException("Rate limit exceeded");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("too.many.requests.title")).thenReturn("Too Many Requests");

    ResponseEntity<?> response = exceptionHandler.handleTooManyRequests(ex, request);

    assertDetails(response, HttpStatus.TOO_MANY_REQUESTS, "Too Many Requests");
  }

  @Test
  @DisplayName("Should handle BusinessException as 422 Unprocessable Entity")
  void shouldHandleBusinessExceptionAsUnprocessableEntity() {
    BusinessException ex = new BusinessException("Business logic failed");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("unprocessable.entity.title")).thenReturn("Unprocessable Entity");

    ResponseEntity<?> response = exceptionHandler.handleBusinessException(ex, request);

    assertDetails(response, HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity");
  }

  @Test
  @DisplayName("Should handle ServiceUnavailableException as 503 Service Unavailable")
  void shouldHandleServiceUnavailableExceptionAsServiceUnavailable() throws Exception {
    ServiceUnavailableException ex = new ServiceUnavailableException("Service down");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("service.unavailable.title")).thenReturn("Service Unavailable");

    ResponseEntity<?> response = exceptionHandler.handleServiceUnavailable(ex, request);

    assertDetails(response, HttpStatus.SERVICE_UNAVAILABLE, "Service Unavailable");
  }

  @Test
  @DisplayName("Should handle generic Exception as 500 Internal Server Error")
  void shouldHandleGenericExceptionAsInternalServerError() {
    Exception ex = new RuntimeException("Internal error");
    HttpServletRequest request = mockRequest();
    when(messageService.getMessage("internal.error.title")).thenReturn("Internal Server Error");

    ResponseEntity<?> response = exceptionHandler.handleGenericException(ex, request);

    assertDetails(response, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
  }
}
