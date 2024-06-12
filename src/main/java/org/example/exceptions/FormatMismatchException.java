package org.example.exceptions;

public class FormatMismatchException extends RuntimeException {
    public FormatMismatchException(String exp) {
        super("The numbers must match in format: " + exp);
    }
}
