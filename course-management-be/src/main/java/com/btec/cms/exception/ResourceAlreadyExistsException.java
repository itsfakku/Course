package com.btec.cms.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

  public ResourceAlreadyExistsException(String property, String value) {
    super(
        String.format(
            "Resource with property %s and value %s already exists."
                + "Make sure to insert a unique value for %s",
            property, value, property));
  }
}
