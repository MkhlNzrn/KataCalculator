package org.example.exceptions;

public class NegativeRomanNumberException extends RuntimeException {
    public NegativeRomanNumberException(String exp) {
        super("The Roman number cannot be negative: " + exp);
    }
}
