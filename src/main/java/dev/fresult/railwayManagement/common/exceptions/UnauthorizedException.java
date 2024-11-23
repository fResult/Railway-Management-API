package dev.fresult.railwayManagement.common.exceptions;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException() {
    super("Unauthorized");
  }

  public UnauthorizedException(String message) {
    super(message);
  }
}
