package com.swanky.commandbus;

import java.io.Serializable;
import java.util.UUID;

public abstract class Command implements Serializable {
    private final UUID correlationId;

    protected Command(UUID correlationId) {
        this.correlationId = correlationId;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }
}
