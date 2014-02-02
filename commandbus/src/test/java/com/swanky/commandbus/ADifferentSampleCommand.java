package com.swanky.commandbus;

import java.util.UUID;

public class ADifferentSampleCommand extends Command {
    protected ADifferentSampleCommand() {
        super(UUID.randomUUID());
    }
}
