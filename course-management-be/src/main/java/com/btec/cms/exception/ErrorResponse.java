package com.btec.cms.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
  private String uri;
  private int status;
  private String message;
}
