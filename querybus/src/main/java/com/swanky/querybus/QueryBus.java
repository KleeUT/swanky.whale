package com.swanky.querybus;

public interface QueryBus {
    void subscribe(Object handler) throws DuplicateHandlerRegisteredForQueryException;

    <T> T sendAndReceive(Query query) throws NoHandlerRegisteredException, UnexpectedQueryReturnType;

    void unsubscribe(Object handler);
}
