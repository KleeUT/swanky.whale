package com.swanky.eventaggregator;

import java.util.UUID;

public class ADifferentSampleEvent extends Event {
    public ADifferentSampleEvent(UUID correlationId) {
        super(correlationId);
    }
}
