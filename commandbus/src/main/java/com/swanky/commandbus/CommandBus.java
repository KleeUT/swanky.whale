package com.swanky.commandbus;

public interface CommandBus {
    void subscribe(Object handler);

    void publish(Command command);

    void unsubscribe(Object handler);
}
