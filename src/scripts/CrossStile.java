package scripts;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import java.util.logging.Logger;

public class CrossStile extends Task<ClientContext> {
    private GameObject stile;
    private Tile stilePosition = new Tile(3199, 3287, 0);

    public CrossStile(ClientContext ctx) {
        super(ctx);
        stile = ctx.objects.name("Stile").peek();
    }

    private void findStile(ClientContext ctx) {
        while (!("Stile".equals(stile.name()))) {
            Logger.getLogger("STILE").info("Looking for stile");
            stile = ctx.objects.name("Stile").poll();
            Logger.getLogger("STILE").info("Found: " + stile.name());
            ctx.camera.angle(ctx.camera.yaw() + 80);
            ctx.movement.step(stilePosition);
        }
        Logger.getLogger("STILE").info("Stile found!");
    }

    public void walkToStile() {
        ctx.movement.step(stilePosition);
        Wait.waitForMovementEnd(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.movement.distance(stile) < 5;
    }

    @Override
    public boolean execute() {
        findStile(ctx);

        Logger.getLogger("STILE").info("Attempting climb...");
        boolean didClimb;
        do {
            findStileView();
            Logger.getLogger("STILE").info("Is stile null? : " + stile.toString() + ", " + stile.name());
            Wait.waitForMovementEnd(ctx);
            didClimb = stile.interact("Climb-over");
            Wait.waitForMovementEnd(ctx);
        } while (!didClimb);
        Logger.getLogger("STILE").info("Climbing over!");

        return true;
    }

    private void findStileView() {
        while (!stile.inViewport()) {
            Logger.getLogger("STILE").info("Stile not in view...");
            ctx.camera.turnTo(stilePosition);
            walkToStile();
            Wait.waitForMovementEnd(ctx);
        }
    }
}
