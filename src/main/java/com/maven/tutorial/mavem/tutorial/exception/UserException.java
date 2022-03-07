package com.maven.tutorial.mavem.tutorial.exception;

public class UserException extends BaseException {

    public UserException(String code) {
        super("user."+code);
    }

    public static UserException UserExceptionNameNull() {
        return new UserException("name.null");
    }

    public static BaseException UserExceptionNotFound() {
        return new UserException("name.not.found");
    }

    public static BaseException LoginFail() {
        return new UserException("login.fail");
    }

    public static BaseException unauthorized() {
        return new UserException("user.unauthorized");
    }
}
