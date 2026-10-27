package com.github.Luythen.GreenHouseServer.Exceptions;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException (String msg) { super(msg); }
}
