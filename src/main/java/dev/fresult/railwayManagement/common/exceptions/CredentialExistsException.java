package dev.fresult.railwayManagement.common.exceptions;

public class CredentialExistsException extends RuntimeException {
  public CredentialExistsException() {
    super("Credential already exists");
  }

  public CredentialExistsException(String message) {
    super(message);
  }
}
