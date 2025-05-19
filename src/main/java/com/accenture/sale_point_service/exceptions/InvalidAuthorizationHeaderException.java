package com.accenture.sale_point_service.exceptions;

public class InvalidAuthorizationHeaderException extends UnauthorizedException {
  public InvalidAuthorizationHeaderException() {
    super("Missing or invalid Authorization header");
  }
}
