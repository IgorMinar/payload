package com.tapdancingmonk.payload.dao;

/**
 *
 * @author Igor Minar
 */
public class EntityNotFoundException extends RuntimeException {

    
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }


    public EntityNotFoundException(String message) {
        super(message);
    }
}
