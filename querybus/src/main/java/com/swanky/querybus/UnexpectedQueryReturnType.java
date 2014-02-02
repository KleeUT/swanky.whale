package com.swanky.querybus;

public class UnexpectedQueryReturnType extends Exception{
    public UnexpectedQueryReturnType(String message, Throwable cause) {
        super(message, cause);
    }
}
