package com.swanky.commandbus;

import java.util.UUID;

public class SampleCommand extends Command {
    protected SampleCommand() {
        super(UUID.randomUUID());
    }
}
