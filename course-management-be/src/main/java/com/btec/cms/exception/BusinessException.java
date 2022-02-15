package com.btec.cms.exception;

public class BusinessException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private String errorCode;
  private String message;

  public BusinessException() {}

  public BusinessException(String errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
