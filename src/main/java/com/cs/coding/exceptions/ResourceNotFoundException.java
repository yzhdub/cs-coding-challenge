package com.cs.coding.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Throwable e) { super(e); }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable e) { super(msg, e); }
}
