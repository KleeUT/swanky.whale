package com.swanky.commandbus;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class CommandBusTests {
    private CommandBus commandBus;
    private SampleCommandHandler commandHandler;

    public void oneClassCanSubscribeToMultipleCommands(){
        given.aCommandHandler().and.aSubscribedSampleCommandHandler()
                .when.aSampleCommandIsPublished().and.aDifferentSampleCommandIsPublished()
                .then.bothCommandsWereHandledByTheCommandHandler();
    }

    private void bothCommandsWereHandledByTheCommandHandler() {
        Assert.assertTrue(commandHandler.isHandled());
        Assert.assertTrue(commandHandler.isOtherHandled());
    }

    private CommandBusTests aDifferentSampleCommandIsPublished() {
       commandBus.publish(new ADifferentSampleCommand());
       return this;
    }

    public void whenAnCommandHandlerHasNotSubscribedToTheCommandHandlerTheCommandIsNotHandled() {
        given.aCommandHandler().and.aUnsubscribedSampleCommandHandler().
                when.aSampleCommandIsPublished().then.theCommandHasNotBeenHandled();
    }

    public void whenAnCommandHandlerHasUnsubscribedFromTheCommandHandlerTheCommandIsNotHandled() {
        given.aCommandHandler().and.aSubscribedSampleCommandHandler().
                and.aSampleCommandIsPublished().then.theCommandHasBeenHandled().
                when.TheCommandHandlerUnsubscribes().and.aSampleCommandIsPublished().
                then.theCommandHasNotBeenHandled();
    }


    public void testThatWhenAnCommandIsPublishedTheSubscriberHandlesIt() {
        given.aCommandHandler().and.aSubscribedSampleCommandHandler().
                when.aSampleCommandIsPublished().then.theCommandHasBeenHandled();
    }

    private CommandBusTests TheCommandHandlerUnsubscribes() {
        commandHandler.unSubscribe();
        return this;
    }

    private CommandBusTests theCommandHasBeenHandled() {
        assertTrue(commandHandler.isHandled());
        return this;
    }

    private CommandBusTests theCommandHasNotBeenHandled() {
        assertFalse(commandHandler.isHandled());
        return this;
    }

    private CommandBusTests aSubscribedSampleCommandHandler() {
        commandHandler = new SampleCommandHandler(commandBus);
        commandHandler.subscribeToCommandHandler();
        return this;
    }

    private CommandBusTests aUnsubscribedSampleCommandHandler() {
        commandHandler = new SampleCommandHandler(commandBus);
        return this;
    }

    private CommandBusTests aSampleCommandIsPublished() {
        commandBus.publish(new SampleCommand());
        return this;
    }

    private CommandBusTests aCommandHandler() {
        commandBus = new SimpleCommandBus();
        return this;
    }

    private CommandBusTests given = this;
    private CommandBusTests when = this;
    private CommandBusTests then = this;
    private CommandBusTests and = this;

}
