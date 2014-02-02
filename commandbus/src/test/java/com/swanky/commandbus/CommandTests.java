package com.swanky.commandbus;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class CommandTests {
    
    public void EachCommandHasUniqueCorrelationId(){
        Command commandOne = new SampleCommand();
        Command commandTwo = new SampleCommand();
        Assert.assertNotEquals(commandOne.getCorrelationId(), commandTwo.getCorrelationId());
    }
}
