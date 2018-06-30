package scripts;

import org.powerbot.script.*;
import org.powerbot.script.rt6.*;
import org.powerbot.script.rt6.ClientContext;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Script.Manifest(name = "Sheep Sheerer", description = "Sheers sheeps", properties = "author=laczeq; topic=999; client=6;")

public class SheepSheerer extends PollingScript<ClientContext> {
    private List<Task> tasks;

    @Override
    public void start() {
        tasks = Arrays.asList(new Shear(ctx), new Bank(ctx));
    }

    @Override
    public void stop() {
    }

    @Override
    public void poll() {
        for (Task task : tasks) {
            if (task.activate()) {
                while (!task.execute()) {

                }
            }

        }
    }

}




