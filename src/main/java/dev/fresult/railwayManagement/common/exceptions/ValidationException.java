package dev.fresult.railwayManagement.common.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException() {
        super("Validation exception");
    }

    public ValidationException(String message) {
        super(message);
    }
}
