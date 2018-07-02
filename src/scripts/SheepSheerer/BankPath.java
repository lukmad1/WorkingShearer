package scripts.SheepSheerer;

import org.powerbot.script.Locatable;
import org.powerbot.script.rt6.ClientContext;
import scripts.util.MovementTask;
import scripts.util.Wait;

import java.util.logging.Logger;

public class BankPath extends MovementTask<ClientContext> {
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public BankPath(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void walk() {

        crossStile();

        logger.info("Locating bank...");
        Locatable nearest = ctx.bank.nearest();
        ctx.camera.turnTo(nearest);
        Wait.waitForMovementEnd(ctx);

        logger.info("Walking to bank...");
        while (!ctx.movement.step(nearest)) {

        }
        Wait.waitForMovementEnd(ctx);
    }

    private void crossStile() {
        StileCrosser stileCrosser = new StileCrosser(ctx);

        logger.info("Trying to cross stile...");
        stileCrosser.execute();
        Wait.waitForMovementEnd(ctx);
    }
}
