package com.github.Luythen.GreenHouseServer.Exceptions;

public class InvalidRefreshToken extends RuntimeException {
    public InvalidRefreshToken (String msg) { super(msg); }
}
