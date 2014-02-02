package com.swanky.eventaggregator;

public class SampleEventHandler {

    private boolean handled = false;
    private boolean otherHandled = false;
    private EventAggregator eventAggregator;

    public SampleEventHandler(EventAggregator eventAggregator){
        this.eventAggregator = eventAggregator;
    }

    public void subscribeToEventHandler(){
        eventAggregator.subscribe(this);
    }

//    @EventHandler
//    public void handle(SampleEvent event) {
//        handled = true;
//    }
//
//    void handle(ADifferentSampleEvent event){
//        otherHandled = true;
//    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public boolean isOtherHandled() {
        return otherHandled;
    }

    public void unSubscribe() {
        eventAggregator.unsubscribe(this);
        handled = false;
    }

//    public <T extends Event> void handle(Event event) {
//    }

    @EventHandler
    public <T extends Event> void handle(SampleEvent event) {
        handled=true;
    }
    @EventHandler
    public <T extends Event> void handle(ADifferentSampleEvent event) {
        otherHandled = true;
    }

    public <T extends Event> void handle(Event event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
