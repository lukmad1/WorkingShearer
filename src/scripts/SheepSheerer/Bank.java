package scripts.SheepSheerer;

import org.powerbot.script.rt6.ClientContext;
import scripts.util.ActionTask;

import java.util.logging.Logger;

public class Bank extends ActionTask<ClientContext> {

    private StileCrosser crossStile = new StileCrosser(ctx);
    private BankPath bankPath = new BankPath(ctx);
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28;
    }

    @Override
    public boolean execute() {

        bankPath.walk();
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
                logger.info("Trying to cross stile...");
            }

            return true;
        }
        return false;
    }

}
