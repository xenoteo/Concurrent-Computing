package xenoteo.com.github.lab10.justPC;

import org.jcsp.lang.*;

/**
 * Producer class. Produces 100 random integers and sends them on output channel and then terminates.
 * The random integers are in the range (0, 100].
 */
public class Producer implements CSProcess {
    private final ChannelOutputInt channelOutput;

    public Producer (final One2OneChannelInt channel) {
        channelOutput = channel.out();
    }

    public void run () {
        for (int i = 0; i < 100; i++){
            int item = (int)(Math.random() * 100) + 1;
            channelOutput.write(item);
            System.out.printf("Produced %d\n", i);
        }
    }
}

