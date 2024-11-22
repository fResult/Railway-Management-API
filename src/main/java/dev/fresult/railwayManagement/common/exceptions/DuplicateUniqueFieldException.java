package dev.fresult.railwayManagement.common.exceptions;

public class DuplicateUniqueFieldException extends RuntimeException {
  public DuplicateUniqueFieldException() {
    super("Duplicate unique field");
  }

  public DuplicateUniqueFieldException(String message) {
    super(message);
  }
}
