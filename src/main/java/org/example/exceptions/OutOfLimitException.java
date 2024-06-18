package org.example.exceptions;

public class OutOfLimitException extends RuntimeException {
    public OutOfLimitException(String exp) {
        super("Number cant be > 10 " + exp);
    }
}
