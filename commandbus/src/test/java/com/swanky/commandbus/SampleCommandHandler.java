package com.swanky.commandbus;

public class SampleCommandHandler {

    private boolean handled = false;
    private boolean otherHandled = false;
    private CommandBus eventAggregator;

    public SampleCommandHandler(CommandBus commandBus){
        this.eventAggregator = commandBus;
    }

    public void subscribeToCommandHandler(){
        eventAggregator.subscribe(this);
    }

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

//    public <T extends Command> void handle(Command event) {
//    }

    @CommandHandler
    public <T extends Command> void handle(SampleCommand event) {
        handled=true;
    }
    @CommandHandler
    public <T extends Command> void handle(ADifferentSampleCommand event) {
        otherHandled = true;
    }

    public <T extends Command> void handle(Command command) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
