package org.example.exceptions;

public class DivisionByZeroException extends RuntimeException {
    public DivisionByZeroException(String exp) {
        super("Division by zero is not allowed in expression: " + exp);
    }
}
