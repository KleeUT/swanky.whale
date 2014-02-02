import com.swanky.querybus.QueryHandler;

public class SampleQueryHandler  {


    @QueryHandler
    public String handle(SampleQuery query) {
        query.setHandled(true);
        return "Handled";  //To change body of implemented methods use File | Settings | File Templates.
    }
    @QueryHandler
    public int handle(AnotherQuery query) {
        query.setHandled(true);
        return 42;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
