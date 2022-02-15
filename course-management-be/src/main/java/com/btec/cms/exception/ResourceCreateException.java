package com.btec.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceCreateException extends RuntimeException {
  private final String message;

  public ResourceCreateException(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
