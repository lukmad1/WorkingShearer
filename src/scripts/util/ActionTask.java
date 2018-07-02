package scripts.util;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

public abstract class ActionTask<C extends ClientContext> extends ClientAccessor<C> {

    public ActionTask(C ctx) {
        super(ctx);
    }

    public abstract boolean activate();

    public abstract boolean execute();

}
