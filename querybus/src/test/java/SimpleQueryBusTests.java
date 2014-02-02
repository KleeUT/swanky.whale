import com.swanky.querybus.*;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@Test
public class SimpleQueryBusTests{

    QueryBus queryBus;
    private SampleQueryHandler queryHandler;
    private String returnedValue = null;
    private int annotherReturnedValue = 0;
    private SampleQuery query;
    private AnotherQuery anotherQuery;

    public void whenAnQueryIsSentThenTheHandlerHandlesIt() throws UnexpectedQueryReturnType, NoHandlerRegisteredException, DuplicateHandlerRegisteredForQueryException {
        given.aSimpleQueryBus().and.aSampleHandler();
        when.aSampleQueryIsPublished();
        then.theQueryIsHandled();
    }

    public void aSingleClassCanHandleTwoQueries() throws DuplicateHandlerRegisteredForQueryException, UnexpectedQueryReturnType, NoHandlerRegisteredException {
        given.aSimpleQueryBus().and.aSampleHandler()
                .when.aSampleQueryIsPublished().and
                .aDifferentQueryIsPublished()
                .then.bothQueriesHaveBeenHandle();

    }

    public void whenAQueryIsSentThenTheHandlerReturnsAValue() throws UnexpectedQueryReturnType, NoHandlerRegisteredException, DuplicateHandlerRegisteredForQueryException {
        given.aSimpleQueryBus().and.aSampleHandler();
        when.aSampleQueryIsPublished();
        then.theReturnedValueIs("Handled");
    }

    public void onlyOneHandlerCanRegisterForAQuery() throws DuplicateHandlerRegisteredForQueryException {
        given.aSimpleQueryBus();
        when.aSampleHandler();
        try {
            and.thisRegisterToHandleTheQuery();
        } catch (DuplicateHandlerRegisteredForQueryException e) {
            return;
        }
        fail("Expected exception not published");
    }

    private void thisRegisterToHandleTheQuery() throws DuplicateHandlerRegisteredForQueryException {
        queryBus.subscribe(this);
    }

    private void theReturnedValueIs(String handled) {
        assertEquals(handled, returnedValue);
    }

    private SimpleQueryBusTests theQueryIsHandled() {
        assertTrue(query.isHandled());
        return this;
    }

    private SimpleQueryBusTests bothQueriesHaveBeenHandle() {
        assertTrue(query.isHandled());
        assertTrue(anotherQuery.isHandled());
        return this;
    }

    private SimpleQueryBusTests aSampleQueryIsPublished() throws UnexpectedQueryReturnType, NoHandlerRegisteredException {
        query = new SampleQuery();
        returnedValue = queryBus.<String>sendAndReceive(query);
        return this;
    }

    private SimpleQueryBusTests aDifferentQueryIsPublished() throws UnexpectedQueryReturnType, NoHandlerRegisteredException {
        anotherQuery = new AnotherQuery();
        annotherReturnedValue = queryBus.sendAndReceive(anotherQuery);
        return this;
    }

    private SimpleQueryBusTests aSampleHandler() throws DuplicateHandlerRegisteredForQueryException {
        queryHandler = new SampleQueryHandler();
        queryBus.subscribe(queryHandler);
        return this;
    }

    private SimpleQueryBusTests aSimpleQueryBus() {
        queryBus = new SimpleQueryBus();
        return this;
    }

    private SimpleQueryBusTests given = this;
    private SimpleQueryBusTests when = this;
    private SimpleQueryBusTests then = this;
    private SimpleQueryBusTests and = this;;


    public String handle(SampleQuery query) {
        throw new RuntimeException("This should not be called");
    }
}
