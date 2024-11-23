package dev.fresult.railwayManagement.common;

import dev.fresult.railwayManagement.common.exceptions.*;

import java.util.HashMap;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseAdviceHandler extends ResponseEntityExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(ResponseAdviceHandler.class);

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    var propertyToError = new HashMap<String, Object>();
    var detail = ProblemDetail.forStatusAndDetail(status, "Invalid request arguments");

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              propertyToError.put(error.getField(), error.getDefaultMessage());
            });
    detail.setProperty("arguments", propertyToError);

    return ResponseEntity.of(detail).build();
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<ProblemDetail> handleConstraintViolation(
      ConstraintViolationException ex) {
    var detail =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request parameters");

    var errors = new HashMap<String, Object>();
    ex.getConstraintViolations()
        .forEach(
            violation -> {
              String path = violation.getPropertyPath().toString();
              String field = path.substring(path.lastIndexOf('.') + 1);
              errors.put(field, violation.getMessage());
            });

    detail.setProperty("arguments", errors);
    logger.error("Constraint violation: {}", errors);

    return ResponseEntity.of(detail).build();
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ProblemDetail> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    var detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

    return ResponseEntity.of(detail).build();
  }

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected ResponseEntity<ProblemDetail> handleValidationException(ValidationException ex) {
    var detail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    logger.info("Validation error: {}", ex.getMessage());

    return ResponseEntity.of(detail).build();
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected ResponseEntity<ProblemDetail> handleUnauthorizedException(UnauthorizedException ex) {
    var detail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    logger.info("Unauthorized: {}", ex.getMessage());

    return ResponseEntity.of(detail).build();
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ResponseEntity<ProblemDetail> handleEntityNotFoundException(
      EntityNotFoundException ex) {
    var detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    logger.info("Entity not found: {}", ex.getMessage());

    return ResponseEntity.of(detail).build();
  }

  @ExceptionHandler(CredentialExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  protected ResponseEntity<ProblemDetail> handleCredentialExistsException(
      CredentialExistsException ex) {
    var detail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    logger.info("Credential exists: {}", ex.getMessage());

    return ResponseEntity.of(detail).build();
  }

  @ExceptionHandler(DuplicateUniqueFieldException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  protected ResponseEntity<ProblemDetail> handleDuplicateUniqueException(
      DuplicateUniqueFieldException ex) {
    System.out.println("Duplicate unique field exception");
    var detail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    logger.info("Duplicate unique field: {}", ex.getMessage());

    return ResponseEntity.of(detail).build();
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<?> handleGlobalException(Exception ex) {
    System.out.println("Global exception");
    var detail =
        ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    logger.error(ex.getMessage());
    ex.printStackTrace();

    return ResponseEntity.of(detail).build();
  }
}
