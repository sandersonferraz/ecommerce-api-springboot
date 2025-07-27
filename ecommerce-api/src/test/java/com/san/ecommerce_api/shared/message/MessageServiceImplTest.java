package com.san.ecommerce_api.shared.message;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("MessageServiceImpl Tests")
@Slf4j
class MessageServiceImplTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    @DisplayName("should return message for Not Found without arguments")
    void shouldReturnMessageNotFoundWithArguments() {
        // Given
        Object[] args = {"123"};
        String expectedMessage = "The requested resource could not be found.";
        extracted(expectedMessage);
        when(messageSource.getMessage(eq("not.found.message"), eq(args), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);
        mockedMessageSource(expectedMessage);
        // When
        String actualMessage = messageService.getMessage("not.found.message", args);
        actualMessage(actualMessage);
        // Then
        assertEquals(expectedMessage, actualMessage);
        testPassed();
    }

    @Test
    @DisplayName("should return message for internal error server with arguments")
    void shouldReturnMessageInternalErrorServerWithArguments() {
        // Given
        Object[] args = {"123"};
        String expectedMessage = "An unexpected error occurred. Please try again later.";
        extracted(expectedMessage);
        when(messageSource.getMessage(eq("internal.error.server.message"), eq(args),
                ArgumentMatchers.any())).thenReturn(expectedMessage);
        mockedMessageSource(expectedMessage);
        // When
        String actualMessage = messageService.getMessage("internal.error.server.message", args);
        actualMessage(actualMessage);
        // Then
        assertThat(actualMessage).isNotNull();
        assertThat(actualMessage).isNotEmpty();
        assertThat(actualMessage).isEqualTo(expectedMessage);
        testPassed();
    }

    @Test
    @DisplayName("should return message for Bad Request with arguments")
    void shouldReturnMessageBadRequestWithArguments() {
        // Given
        Object[] args = {"123"};
        String expectedMessage = "The request could not be understood by the server due to malformed syntax.";
        extracted(expectedMessage);
        when(messageSource.getMessage(eq("bad.request.message"), eq(args), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);
        mockedMessageSource(expectedMessage);
        // When
        String actualMessage = messageService.getMessage("bad.request.message", args);
        actualMessage(actualMessage);
        // Then
        assertThat(actualMessage).isNotNull();
        assertThat(actualMessage).isNotEmpty();
        assertThat(actualMessage).isEqualTo(expectedMessage);
        testPassed();
    }

    @Test
    @DisplayName("should return message for Unauthorized with arguments")
    void shouldReturnMessageUnauthorizedWithArguments() {
        // Given
        Object[] args = {"123"};
        String expectedMessage = "You must be authenticated to access this resource.";
        extracted(expectedMessage);
        when(messageSource.getMessage(eq("unauthorized.message"), eq(args), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);
        mockedMessageSource(expectedMessage);
        // When
        String actualMessage = messageService.getMessage("unauthorized.message", args);
        actualMessage(actualMessage);
        // Then
        assertThat(actualMessage).isNotNull();
        assertThat(actualMessage).isNotEmpty();
        assertThat(actualMessage).isEqualTo(expectedMessage);
        testPassed();
    }

    @Test
    @DisplayName("should return message for Forbidden with arguments")
    void shouldReturnMessageForbiddenWithArguments() {
        // Given
        Object[] args = {"123"};
        String expectedMessage = "You do not have permission to access this resource.";
        extracted(expectedMessage);
        when(messageSource.getMessage(eq("forbidden.message"), eq(args), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);
        mockedMessageSource(expectedMessage);
        // When
        String actualMessage = messageService.getMessage("forbidden.message", args);
        actualMessage(actualMessage);
        // Then
        assertThat(actualMessage).isNotNull();
        assertThat(actualMessage).isNotEmpty();
        assertThat(actualMessage).isEqualTo(expectedMessage);
        testPassed();
    }

    @Test
    @DisplayName("should return null when message not found")
    void shouldReturnNullWhenMessageNotFound() {
        // Given
        Object[] args = {"123"};
        String messageKey = "non.existent.message";
        extracted("Expected message to be null for non-existent key.");
        when(messageSource.getMessage(eq(messageKey), eq(args), ArgumentMatchers.any()))
                .thenThrow(new NoSuchMessageException(messageKey));

        assertThatThrownBy(() -> messageService.getMessage(messageKey, args))
                .isInstanceOf(NoSuchMessageException.class)
                .hasMessageContaining(messageKey);
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
