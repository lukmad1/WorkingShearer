package scripts.SheepSheerer;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Random;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import scripts.util.ActionTask;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


@Script.Manifest(name = "Sheep Sheerer", description = "Sheers sheeps", properties = "author=laczeq; topic=999; client=6;")

public class SheepSheerer extends PollingScript<ClientContext> {
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private List<ActionTask> tasks;
    private Bank bankTask = new Bank(ctx);
    private ShearHelper shearTask = new ShearHelper(ctx);

    @Override
    public void start() {
        tasks = Arrays.asList(shearTask, bankTask);

    }

    @Override
    public void stop() {
    }

    @Override
    public void poll() {

        for (ActionTask task : tasks) {
            if (task.activate()) {
                while (!task.execute()) {
                    antiIdler();
                }
            }

        }
    }


    private void antiIdler() {
        if (!ctx.players.local().inMotion()) {
            logger.info("Anti idling...");
            ctx.camera.angle(ctx.camera.yaw() + Random.nextInt(-100, 100));
            ctx.camera.pitch(ctx.camera.pitch() + Random.nextInt(-10, 10));
        }
    }


}




