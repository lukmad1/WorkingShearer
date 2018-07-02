package scripts.SheepSheerer;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import scripts.util.ActionTask;
import scripts.util.Wait;

import java.util.logging.Logger;

public class StileCrosser extends ActionTask<ClientContext> {
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private GameObject stile;
    private Tile stilePosition = new Tile(3199, 3287, 0);

    public StileCrosser(ClientContext ctx) {
        super(ctx);
        stile = ctx.objects.name("Stile").peek();
    }



    @Override
    public boolean activate() {
        return ctx.movement.distance(stile) < 5;
    }

    @Override
    public boolean execute() {
        findStile(ctx);

        logger.info("Attempting climb...");
        boolean didClimb;
        do {
            findStileView();
            logger.info("Is stile null? : " + stile.toString() + ", " + stile.name());
            Wait.waitForMovementEnd(ctx);
            didClimb = stile.interact("Climb-over");
            Wait.waitForMovementEnd(ctx);
        } while (!didClimb);
        logger.info("Climbing over!");

        return true;
    }

    private void findStileView() {
        while (!stile.inViewport()) {
            logger.info("Stile not in view...");
            ctx.camera.turnTo(stilePosition);
            walkToStile();
            Wait.waitForMovementEnd(ctx);
        }
    }

    private void walkToStile() {
        ctx.movement.step(stilePosition);
        Wait.waitForMovementEnd(ctx);
    }

    private void findStile(ClientContext ctx) {
        while (!("Stile".equals(stile.name()))) {
            logger.info("Looking for stile...");
            ctx.camera.angle(ctx.camera.yaw() + 80);
            ctx.movement.step(stilePosition);

            stile = ctx.objects.name("Stile").poll();
            logger.info("Found: " + stile.name());
        }
        logger.info("Stile found!");
    }


}
