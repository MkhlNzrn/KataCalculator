package org.example.exceptions;

public class IncorrectNumbersCountException extends RuntimeException {
    public IncorrectNumbersCountException(String exp) {
        super("The numbers must be exactly two: " + exp);
    }
}
