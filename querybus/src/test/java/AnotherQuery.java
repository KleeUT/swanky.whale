import com.swanky.querybus.Query;

public class AnotherQuery extends Query {
    private boolean handled = false;

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }
}
