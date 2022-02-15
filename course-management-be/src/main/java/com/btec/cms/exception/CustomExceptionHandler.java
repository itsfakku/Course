package com.btec.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handlerResourceNotFoundException(
      ResourceNotFoundException ex, HttpServletRequest req) {
    ErrorResponse err =
        new ErrorResponse(req.getRequestURI(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<?> handlerResourceAlreadyExistsException(
      ResourceAlreadyExistsException ex, HttpServletRequest req) {
    ErrorResponse err =
        new ErrorResponse(req.getRequestURI(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceCreateException.class)
  public ResponseEntity<?> handlerResourceCreateException(
      ResourceCreateException ex, HttpServletRequest req) {
    ErrorResponse err =
        new ErrorResponse(req.getRequestURI(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceEditException.class)
  public ResponseEntity<?> handlerResourceEditException(
      ResourceEditException ex, HttpServletRequest req) {
    ErrorResponse err =
        new ErrorResponse(req.getRequestURI(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceDeleteException.class)
  public ResponseEntity<?> handlerResourceDeleteException(
      ResourceDeleteException ex, HttpServletRequest req) {
    ErrorResponse err =
        new ErrorResponse(req.getRequestURI(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<?> handlerInternalServerException(
      InternalServerException ex, HttpServletRequest req) {
    ErrorResponse err =
        new ErrorResponse(
            req.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handlerIllegalArgumentException(
      IllegalArgumentException ex, HttpServletRequest req) {
    ErrorResponse err =
        new ErrorResponse(req.getRequestURI(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handlerException(Exception ex, HttpServletRequest req) {
    ErrorResponse err =
        new ErrorResponse(
            req.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!" + exc.getMessage());
  }
}
