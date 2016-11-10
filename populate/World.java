package populate;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.ActorRef;

public class World extends UntypedActor {

    // final summed value
    private static double sum = 0.0;
    // current number of actors remaining
    private static Integer numActors = Config.NUM_ACTORS;

    /**
     * Adds the returned sum from the sending actor to the total sum. Once all
     * actors are recieved, will print final sum.
     */
    public void onReceive(Object msg) {
        if (numActors == 0) {
                System.out.println(sum);
                getContext().stop(getSelf());
        } else if (msg != null) {
            sum += (int) msg
            numActors--;
        } else {
            unhandled(msg);
        }
    }

    /**
     * Creates number of actors equal to NUM_ACTORS.
     */
    @Override
    public void preStart() {
        for (int i = 0; i < Config.NUM_ACTORS; i++) {
            final ActorRef counter = getContext().actorOf(
                    Props.create(Counter.class),
                    "counter" + Integer.toString(i));
            // pass i to the i-th actor for it to know which numbers to sum
            counter.tell(i, getSelf());
        }
    }
}
