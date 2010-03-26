package com.tapdancingmonk.payload;

public class FubarException extends RuntimeException {
    public FubarException() {
        super();
    }

    public FubarException(String msg) {
        super(msg);
    }
    
    public FubarException(String msg, Throwable ex) {
        super(msg, ex);
    }
    
    public FubarException(Throwable ex) {
        super(ex);
    }
}
