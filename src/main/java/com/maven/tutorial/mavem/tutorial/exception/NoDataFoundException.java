package com.maven.tutorial.mavem.tutorial.exception;

public class NoDataFoundException  extends RuntimeException {
    public NoDataFoundException() {
        super("No data found");
    }
}
