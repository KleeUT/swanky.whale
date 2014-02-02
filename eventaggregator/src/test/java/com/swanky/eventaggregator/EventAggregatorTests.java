package com.swanky.eventaggregator;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class EventAggregatorTests {
    private EventAggregator eventAggregator;
    private SampleEventHandler eventHandler;

    public void oneClassCanSubscribeToMultipleEvents(){
        given.anEvenAggregator().and.aSubscribedSampleEventHandler()
                .when.aSampleEventIsPublished().and.aDifferentSampleEventIsPublished()
                .then.bothEventsWereHandledByTheEventHandler();
    }

    private void bothEventsWereHandledByTheEventHandler() {
        Assert.assertTrue(eventHandler.isHandled());
        Assert.assertTrue(eventHandler.isOtherHandled());
    }

    private EventAggregatorTests aDifferentSampleEventIsPublished() {
       eventAggregator.publish(new ADifferentSampleEvent(idTwo));
       return this;
    }

    public void whenAnEventHandlerHasNotSubscribedToTheEventHandlerTheEventIsNotHandled() {
        given.anEvenAggregator().and.aUnsubscribedSampleEventHandler().
                when.aSampleEventIsPublished().then.theEventHasNotBeenHandled();
    }

    public void whenAnEventHandlerHasUnsubscribedFromTheEventHandlerTheEventIsNotHandled() {
        given.anEvenAggregator().and.aSubscribedSampleEventHandler().
                and.aSampleEventIsPublished().then.theEventHasBeenHandled().
                when.TheEventHandlerUnsubscribes().and.aSampleEventIsPublished().
                then.theEventHasNotBeenHandled();
    }


    public void testThatWhenAnEventIsPublishedTheSubscriberHandlesIt() {
        given.anEvenAggregator().and.aSubscribedSampleEventHandler().
                when.aSampleEventIsPublished().then.theEventHasBeenHandled();
    }

    private EventAggregatorTests TheEventHandlerUnsubscribes() {
        eventHandler.unSubscribe();
        return this;
    }

    private EventAggregatorTests theEventHasBeenHandled() {
        assertTrue(eventHandler.isHandled());
        return this;
    }

    private EventAggregatorTests theEventHasNotBeenHandled() {
        assertFalse(eventHandler.isHandled());
        return this;
    }

    private EventAggregatorTests aSubscribedSampleEventHandler() {
        eventHandler = new SampleEventHandler(eventAggregator);
        eventHandler.subscribeToEventHandler();
        return this;
    }

    private EventAggregatorTests aUnsubscribedSampleEventHandler() {
        eventHandler = new SampleEventHandler(eventAggregator);
        return this;
    }

    private EventAggregatorTests aSampleEventIsPublished() {
        eventAggregator.publish(new SampleEvent(idOne));
        return this;
    }

    private EventAggregatorTests anEvenAggregator() {
        eventAggregator = new SimpleEventAggregator();
        return this;
    }

    private UUID idOne = UUID.randomUUID();
    private UUID idTwo = UUID.randomUUID();

    private EventAggregatorTests given = this;
    private EventAggregatorTests when = this;
    private EventAggregatorTests then = this;
    private EventAggregatorTests and = this;

}
