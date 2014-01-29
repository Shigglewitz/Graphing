package org.dkeeney.graphing.equations.exceptions;

public class InvalidParenthesisException extends RuntimeException {

    private static final long serialVersionUID = 3919541076117625017L;

    public InvalidParenthesisException(String message) {
        super(message);
    }
}
