package com.github.Luythen.GreenHouseServer.Exceptions;

public class GreenHouseAlreadyExitsException extends RuntimeException {
    public GreenHouseAlreadyExitsException () { super("Användaren har redan ett växthus"); }
}
