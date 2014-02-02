package com.swanky.querybus;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleQueryBus implements QueryBus {
    Map<Class<? extends Query>, Object> Objects;
    private static Logger LOG = Logger.getLogger(QueryBus.class);

    public SimpleQueryBus() {
        Objects = new HashMap<Class<? extends Query>, Object>();
    }

    @Override
    public void subscribe(Object handler) throws DuplicateHandlerRegisteredForQueryException {
        Class clazz = handler.getClass();
        for (Method method : clazz.getMethods()) {
            if (methodIsObject(method)) {
                Class[] parameters = method.getParameterTypes();
                if (parameters.length == 1) {
                    subscribeClassToQuery(handler, parameters[0]);
                }
            }
        }

    }

    private boolean methodIsObject(Method method) {
        return method.getName().equals("handle") &&
                method.getParameterTypes().length == 1 &&
                classIsQuery(method.getParameterTypes()[0]);
    }

    private boolean classIsQuery(Class clazz) {
        return Query.class.isAssignableFrom(clazz) || clazz.isAssignableFrom(Query.class);
    }

    private void subscribeClassToQuery(Object handler, Class<? extends Query> queryToHandle) throws DuplicateHandlerRegisteredForQueryException {
        if (Objects.keySet().contains(queryToHandle)) {
            LOG.error(String.format("Object for %s", queryToHandle.getName()));
            throw new DuplicateHandlerRegisteredForQueryException(String.format("Object for %s", queryToHandle.getName()));
        } else {
            Objects.put(queryToHandle, handler);
        }
    }

    @Override
    public <T> T sendAndReceive(Query query) throws NoHandlerRegisteredException, UnexpectedQueryReturnType {
        Class<? extends Query> type = query.getClass();
        Object handler = Objects.get(query.getClass());
        if (handler == null) {
            LOG.info("No query handler declared for query " + type.getName());
            throw new NoHandlerRegisteredException("");
        }
        for (Method method : handler.getClass().getMethods()) {
            if (method.isAnnotationPresent(QueryHandler.class)
                    && method.getParameterTypes().length == 1
                    && method.getParameterTypes()[0].equals(query.getClass())) {
                try {
                    return (T) method.invoke(handler, query);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (ClassCastException classCastException) {
                    throw new UnexpectedQueryReturnType(String.format("Unexpected Result of type %s", UnexpectedQueryReturnType.class.getSimpleName()), classCastException);
                }
            }
        }
        throw new NoHandlerRegisteredException(String.format("Could not find handler for query: :s", query));
    }

    @Override
    public void unsubscribe(Object handler) {
        List<Map.Entry> entriesToRemove = new ArrayList<Map.Entry>();
        for (Map.Entry entry : Objects.entrySet()) {
            if (entry.getValue().equals(handler)) {
                entriesToRemove.add(entry);
            }
        }
        for (Map.Entry entry : entriesToRemove) {
            Objects.remove(entry.getKey());
        }
    }
}
