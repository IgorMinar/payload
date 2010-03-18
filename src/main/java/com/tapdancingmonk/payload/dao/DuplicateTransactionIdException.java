package com.tapdancingmonk.payload.dao;

public class DuplicateTransactionIdException extends RuntimeException {

    public DuplicateTransactionIdException() {
        super();
    }

    public DuplicateTransactionIdException(String message) {
        super(message);
    }

    public DuplicateTransactionIdException(Throwable cause) {
        super(cause);
    }

    public DuplicateTransactionIdException(String message, Throwable cause) {
        super(message, cause);
    }

}
