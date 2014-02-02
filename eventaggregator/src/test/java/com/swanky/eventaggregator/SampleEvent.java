package com.swanky.eventaggregator;

import java.util.UUID;

public class SampleEvent extends Event {
    public SampleEvent(UUID correlationId) {
        super(correlationId);
    }
}
