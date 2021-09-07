package com.cs.coding.exceptions;

public class ResourceNotSpecifiedException extends RuntimeException {

    public ResourceNotSpecifiedException(Throwable e) { super(e); }

    public ResourceNotSpecifiedException(String msg) {
        super(msg);
    }

    public ResourceNotSpecifiedException(String msg, Throwable e) { super(msg, e); }
}
