package com.maven.tutorial.mavem.tutorial.exception;

public abstract class BaseException extends Exception {

    public BaseException(String code) {
        super(code);
    }
}
