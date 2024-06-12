package org.example.exceptions;

public class IncorrectExpressionException extends RuntimeException {
    public IncorrectExpressionException(String exp) {
        super("Invalid expression " + exp);
    }
}
