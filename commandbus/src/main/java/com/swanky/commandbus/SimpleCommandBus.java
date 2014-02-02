package com.swanky.commandbus;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleCommandBus implements CommandBus {
    Map<Class<? extends Command>, List<Object>> eventHandlers;
    private static Logger LOG = Logger.getLogger(CommandBus.class);
    public SimpleCommandBus(){
        eventHandlers = new HashMap<Class<? extends Command>, List<Object>>();
    }

    @Override
    public void subscribe(Object handler){
        Class clazz = handler.getClass();
        for(Method method : clazz.getMethods()){
            if(methodIsEventHandler(method)){
                Class[] parameters = method.getParameterTypes();
                if(parameters.length == 1){
                    subscribeClassToEvent(handler, parameters[0]);
                }
            }
        }

    }

    private boolean methodIsEventHandler(Method method) {
        return method.isAnnotationPresent(CommandHandler.class) &&
                method.getParameterTypes().length == 1 &&
                classIsEvent(method.getParameterTypes()[0]);
    }

    private boolean classIsEvent(Class clazz){
        return Command.class.isAssignableFrom(clazz) || clazz.isAssignableFrom(Command.class);
    }

    private void subscribeClassToEvent(Object handler, Class<? extends Command> eventToHandle) {
        if(eventHandlers.keySet().contains(eventToHandle)){
            eventHandlers.get(eventToHandle).add(handler);

        }else{
            List<Object> handlers = new ArrayList<Object>();
            handlers.add(handler);
            eventHandlers.put(eventToHandle, handlers);
        }
    }

    @Override
    public void publish(Command command){
        Class<? extends Command> type = command.getClass();
        List<Object> handlers = eventHandlers.get(command.getClass());
        if(handlers == null){
            LOG.info("No command handlers declared for command " + type.getName());
            return;
        }
        for(Object handler : handlers){
            for(Method method : handler.getClass().getMethods())
            {
                if(method.isAnnotationPresent(CommandHandler.class) &&
                        method.getParameterTypes().length == 1  &&
                        method.getParameterTypes()[0].equals(command.getClass())){
                    try {
                        method.invoke(handler, command);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void unsubscribe(Object handler) {
        for(List<Object> handlers : eventHandlers.values()){
            handlers.remove(handler);
        }
    }
}
