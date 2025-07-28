package com.san.ecommerce_api.shared.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@DisplayName("MessageServiceImpl Unit Tests")
@Slf4j
class MessageServiceImplTest {

  @Mock
  private MessageSource messageSource;

  @InjectMocks
  private MessageServiceImpl messageService;



  @Test
  @DisplayName("should return generic Bad Request message")
  void shouldReturnBadRequestMessage() {
    assertMessage("bad.request.message", "The request could not be understood due to malformed syntax.", "123");
  }

  @Test
  @DisplayName("should return generic Unauthorized message")
  void shouldReturnUnauthorizedMessage() {
    assertMessage("unauthorized.message", "You must be authenticated to access this resource.", "123");
  }

  @Test
  @DisplayName("should return generic Forbidden message")
  void shouldReturnForbiddenMessage() {
    assertMessage("forbidden.message", "You do not have permission to access this resource.", "123");
  }

  @Test
  @DisplayName("should return generic Not Found message")
  void shouldReturnNotFoundMessage() {
    assertMessage("not.found.message", "The requested resource could not be found.", "123");
  }

  @Test
  @DisplayName("should return generic Method Not Allowed message")
  void shouldReturnMethodNotAllowedMessage() {
    assertMessage("method.not.allowed.message", "The HTTP method used is not supported for this endpoint.", "POST", "/users");
  }

  @Test
  @DisplayName("should return generic Not Acceptable message")
  void shouldReturnNotAcceptableMessage() {
    assertMessage("not.acceptable.message", "The requested format is not supported.", "application/xml");
  }

  @Test
  @DisplayName("should return generic Conflict message")
  void shouldReturnConflictMessage() {
    assertMessage("conflict.message", "There was a conflict with the current state of the resource.", "Email already exists");
  }

  @Test
  @DisplayName("should return generic Unprocessable Entity message")
  void shouldReturnUnprocessableEntityMessage() {
    assertMessage("unprocessable.entity.message", "The server understands the request but cannot process it.", "Email is invalid");
  }

  @Test
  @DisplayName("should return generic Too Many Requests message")
  void shouldReturnTooManyRequestsMessage() {
    assertMessage("too.many.requests.message", "You have sent too many requests in a given amount of time.", "60");
  }

  @Test
  @DisplayName("should return generic Internal Server Error message")
  void shouldReturnInternalServerErrorMessage() {
    assertMessage("internal.error.server.message", "An unexpected error occurred. Please try again later.", "createUser");
  }

  @Test
  @DisplayName("should return generic Service Unavailable message")
  void shouldReturnServiceUnavailableMessage() {
    assertMessage("service.unavailable.message", "The service is temporarily unavailable. Please try again later.", "30");
  }

  // --- DETALHADAS ---

  @Test
  @DisplayName("should return Bad Request detail message")
  void shouldReturnBadRequestDetail() {
    assertMessage("bad.request.message.detail", "The request to \"/users\" is malformed. Cause: Missing email", "/users", "Missing email");
  }

  @Test
  @DisplayName("should return Unauthorized detail message")
  void shouldReturnUnauthorizedDetail() {
    assertMessage("unauthorized.message.detail", "Authentication is required to access \"/admin\". Please log in.", "/admin");
  }

  @Test
  @DisplayName("should return Forbidden detail message")
  void shouldReturnForbiddenDetail() {
    assertMessage("forbidden.message.detail", "Access denied for resource \"/admin\". Check your permissions.", "/admin");
  }

  @Test
  @DisplayName("should return Not Found detail message")
  void shouldReturnNotFoundDetail() {
    assertMessage("not.found.message.detail", "The resource \"User\" with ID \"42\" could not be found.", "User", "42");
  }

  @Test
  @DisplayName("should return Method Not Allowed detail message")
  void shouldReturnMethodNotAllowedDetail() {
    assertMessage("method.not.allowed.message.detail", "The method \"PUT\" is not allowed for the resource \"/products\".", "PUT", "/products");
  }

  @Test
  @DisplayName("should return Not Acceptable detail message")
  void shouldReturnNotAcceptableDetail() {
    assertMessage("not.acceptable.message.detail", "Cannot produce a response in the requested format: \"text/csv\".", "text/csv");
  }

  @Test
  @DisplayName("should return Conflict detail message")
  void shouldReturnConflictDetail() {
    assertMessage("conflict.message.detail", "Conflict occurred: Email already in use", "Email already in use");
  }

  @Test
  @DisplayName("should return Unprocessable Entity detail message")
  void shouldReturnUnprocessableEntityDetail() {
    assertMessage("unprocessable.entity.message.detail", "Validation failed: CPF is invalid", "CPF is invalid");
  }

  @Test
  @DisplayName("should return Too Many Requests detail message")
  void shouldReturnTooManyRequestsDetail() {
    assertMessage("too.many.requests.message.detail", "Rate limit exceeded. Try again after 60 seconds.", "60");
  }

  @Test
  @DisplayName("should return Internal Server Error detail message")
  void shouldReturnInternalServerErrorDetail() {
    assertMessage("internal.error.server.message.detail", "An unexpected error occurred while processing \"createUser\". Try again later.", "createUser");
  }

  @Test
  @DisplayName("should return Service Unavailable detail message")
  void shouldReturnServiceUnavailableDetail() {
    assertMessage("service.unavailable.message.detail", "The service is down for maintenance or overloaded. Retry after 30.", "30");
  }

  // --- Fallback (mensagem não encontrada) ---

  @Test
  @DisplayName("should throw NoSuchMessageException when key not found")
  void shouldThrowWhenMessageKeyDoesNotExist() {
    // Given
    String key = "non.existent.message";
    Object[] args = {"value"};
    when(messageSource.getMessage(eq(key), eq(args), ArgumentMatchers.any()))
            .thenThrow(new NoSuchMessageException(key));

    // Then
    assertThatThrownBy(() -> messageService.getMessage(key, args))
            .isInstanceOf(NoSuchMessageException.class)
            .hasMessageContaining(key);
  }

  // --- Helper comum para todos os testes ---
  private void assertMessage(String key, String expected, Object... args) {
    extracted(expected);
    when(messageSource.getMessage(eq(key), eq(args), ArgumentMatchers.any()))
            .thenReturn(expected);
    mockedMessageSource(expected);

    String actual = messageService.getMessage(key, args);
    actualMessage(actual);

    assertThat(actual).isNotNull().isNotEmpty().isEqualTo(expected);
    testPassed();
  }

  private static void testPassed() {
    log.info("Test passed: Actual message matches expected message.");
  }

  private static void actualMessage(String actualMessage) {
    log.info("Actual message: {}", actualMessage);
  }

  private static void mockedMessageSource(String expectedMessage) {
    log.info("Mocked messageSource to return: {}", expectedMessage);
  }

  private static void extracted(String expectedMessage) {
    log.info("Expected message: {}", expectedMessage);
  }

}
