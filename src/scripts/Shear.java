package scripts;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;


public class Shear extends Task<ClientContext> {
    private int SHEEP_ID[] = {43, 5160, 5161, 5157, 1765};


    public Shear(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() < 28
                && !ctx.npcs.select().id(SHEEP_ID).isEmpty()
                && ctx.players.local().animation() == -1;

    }

    @Override
    public boolean execute() {
        Npc sheep = ctx.npcs.id(SHEEP_ID).nearest().poll();
        if (sheep.inViewport()) {
            sheep.interact("Shear");
        } else {
            ctx.movement.step(sheep);
            ctx.camera.turnTo(sheep);
        }
        return true;
    }
}
