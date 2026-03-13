package com.example.delivery.infrastructure.rest.advice;

import com.example.delivery.domain.exception.client.ClientConflictException;
import com.example.delivery.domain.exception.client.ClientNotFoundException;
import com.example.delivery.domain.exception.client.ClientValidationException;
import com.example.delivery.domain.exception.order.OrderNotFoundException;
import com.example.delivery.domain.exception.product.ProductInvaliUuidException;
import com.example.delivery.domain.exception.product.ProductNotFoundException;
import com.example.delivery.infrastructure.rest.dto.response.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final ZoneId API_ZONE = ZoneId.of("America/Bogota");

    private ResponseEntity<ErrorResponseDto> buildError(HttpStatus status, String code, String description, Throwable ex) {
        String timestamp = OffsetDateTime.now(API_ZONE).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        String exception = ex == null ? null : ex.getClass().getSimpleName();
        return ResponseEntity.status(status)
                .body(new ErrorResponseDto(code, timestamp, description, exception));
    }

    // Excepciones Client
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleClientNotFound(ClientNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, "E1003", ex.getMessage(), ex);
    }

    @ExceptionHandler(ClientConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleClienConflictException(ClientConflictException ex) {
        return buildError(HttpStatus.CONFLICT, "E1002", ex.getMessage(), ex);
    }

    @ExceptionHandler(ClientValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleClientValidationException(ClientValidationException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "E1001", ex.getMessage(), ex);
    }

    // Excepciones Product
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFound(ProductNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, "E1003", ex.getMessage(), ex);
    }

    @ExceptionHandler(ProductInvaliUuidException.class)
    public ResponseEntity<ErrorResponseDto> handleProductInvaliUuid(ProductInvaliUuidException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "E1004", ex.getMessage(), ex);
    }

    // Excepciones Order
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFound(OrderNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, "E1003", ex.getMessage(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String description = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));
        return buildError(HttpStatus.BAD_REQUEST, "E1001", description, ex);
    }

    // UUID inválido en path variable (Spring no puede convertir el String a UUID)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String detail = "El parámetro '" + ex.getName() + "' tiene un formato inválido: " + ex.getValue();
        return buildError(HttpStatus.BAD_REQUEST, "E1004", detail, ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex) {
        String detail = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
        return buildError(HttpStatus.BAD_REQUEST, "E1001", detail, ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "E1004", "El cuerpo de la solicitud es inválido o tiene formato incorrecto", ex);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingParameter(MissingServletRequestParameterException ex) {
        String detail = "Falta el parámetro requerido: " + ex.getParameterName();
        return buildError(HttpStatus.BAD_REQUEST, "E1001", detail, ex);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return buildError(HttpStatus.METHOD_NOT_ALLOWED, "E1005", ex.getMessage(), ex);
    }

    // Timestamp inválido o argumentos de negocio inválidos → 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "E1001", ex.getMessage(), ex);
    }

    // Error general del servidor → 500 con traza en log
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "E1500",
                "Ocurrió un error inesperado. Consulte los logs del servidor para más detalles.",
                ex
        );
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
