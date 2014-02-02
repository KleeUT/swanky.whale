package com.swanky.eventaggregator;

import java.io.Serializable;
import java.util.UUID;

public abstract class Event implements Serializable {
    protected final UUID correlationId;
    public Event(UUID correlationId){
        this.correlationId = correlationId;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }
}
