package com.github.Luythen.GreenHouseServer.Exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) { super("E-postadressen används redan"); }
}
