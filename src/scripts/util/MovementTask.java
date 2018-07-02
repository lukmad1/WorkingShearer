package scripts.util;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

public abstract class MovementTask<C extends ClientContext> extends ClientAccessor<C> {


    public MovementTask(C ctx) {
        super(ctx);
    }

    public abstract void walk();

}
