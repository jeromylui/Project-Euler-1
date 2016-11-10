package populate;

import akka.actor.UntypedActor;
import java.util.ArrayList;

public class Counter extends UntypedActor {

    private int sum;
    private int id;

    private int checkFactor() {
        int start = i*(Config.UPPER_BOUND/Config.NUM_ACTORS);
        int end = (i + 1)*(Config.UPPER_BOUND/Config.NUM_ACTORS);

        for (int i = start; i <= end; i++){
            for (int j: Config.FACTORS){
                if (i % j == 0){
                    sum += i;
                    break;
                }
            }
        }

        return sum;
    }

    @Override
    public void onReceive(Object msg) {
        if (msg != null) {
            this.id = (int) msg;
            getSender().tell(checkFactor(), getSelf());
        } else {
            getContext().stop(getSelf());
            unhandled(msg);
        }
    }

    @Override
    public void preStart() {
        sum = 0;
    }
}
