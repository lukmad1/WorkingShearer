package scripts.SheepSheerer;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;
import scripts.util.ActionTask;
import scripts.util.Wait;

import java.time.LocalTime;
import java.util.logging.Logger;

import static java.time.temporal.ChronoUnit.SECONDS;


public class ShearHelper extends ActionTask<ClientContext> {
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private int sheepCount = 0;
    private int SHEEP_ID[] = {43, 5160, 5161, 5157, 1765};
    private int startingBackpackCount;
    private LocalTime startTime = LocalTime.now();

    public ShearHelper(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        startingBackpackCount = ctx.backpack.select().count();
        return startingBackpackCount < 28
                && !ctx.npcs.select().id(SHEEP_ID).isEmpty()
                && ctx.players.local().animation() == -1;

    }

    @Override
    public boolean execute() {
        Npc sheep = ctx.npcs.id(SHEEP_ID).nearest().poll();

        if (sheep.inViewport()) {
            if (sheep.interact("Shear")) {
                Wait.waitForMovementEnd(ctx);
                logger.info("Shearing sheep...");
            }
        } else {
            ctx.camera.turnTo(sheep);
        }

        if (ctx.backpack.select().count() > startingBackpackCount) {
            sheepCount++;
            logSheepCountStats();
        }


        return true;
    }


    private void logSheepCountStats() {
        LocalTime actuallTime = LocalTime.now();
        long seconds = startTime.until(actuallTime, SECONDS);
        long hours = seconds / (3600);
        seconds = seconds % (3600);
        long minutes = seconds / 60;
        seconds = seconds % 60;


        logger.info("Sheeps : " + sheepCount + " @ " + hours + ":" + minutes + ":" + seconds);
    }

}
