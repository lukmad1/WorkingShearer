package scripts;

import org.powerbot.script.Locatable;
import org.powerbot.script.rt6.ClientContext;

import java.util.logging.Logger;

public class Bank extends Task<ClientContext> {

    private CrossStile crossStile = new CrossStile(ctx);

    public Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28;
    }

    @Override
    public boolean execute() {

//        if (!crossStile.activate()) {
//            crossStile.walkToStile();
//            Logger.getLogger("BANK").info("Walking to stile...");
//        }


        Logger.getLogger("BANK").info("Trying to cross stile...");
        crossStile.execute();
        Wait.waitForMovementEnd(ctx);

        Logger.getLogger("BANK").info("Locating bank...");
        Locatable nearest = ctx.bank.nearest();
        ctx.camera.turnTo(nearest);
        Wait.waitForMovementEnd(ctx);
        Logger.getLogger("BANK").info("Walking to bank...");
        while (!ctx.movement.step(nearest)) {

        }
        Wait.waitForMovementEnd(ctx);

        org.powerbot.script.rt6.Bank bank = ctx.bank;
        if (bank.inViewport()) {
            while (!bank.opened()) {
                bank.open();
            }

            while (ctx.backpack.select().count() == 28) {
                bank.depositInventory();
            }

            while (ctx.bank.opened()) {
                bank.close();
            }

            while (!crossStile.execute()) {
                Logger.getLogger("BANK").info("Trying to cross stile...");
            }

            return true;
        }
        return false;
    }

}
