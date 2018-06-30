package scripts;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class Wait {

    public static void waitForMovementEnd(ClientContext ctx) {
        Condition.wait(() -> !ctx.players.local().inMotion());
    }

    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
