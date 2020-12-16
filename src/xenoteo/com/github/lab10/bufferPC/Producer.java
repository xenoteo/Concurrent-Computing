package xenoteo.com.github.lab10.bufferPC;

import org.jcsp.lang.*;

/**
 * Producer class. Produces 100 random integers and sends them on output channel, then sends -1 and terminates.
 * The random integers are in the range (0, 100].
 */
public class Producer implements CSProcess {
    private final ChannelOutputInt channelOutput;
    private final int id;

    public Producer (final One2OneChannelInt channel, int id) {
        channelOutput = channel.out();
        this.id = id;
    }

    public void run (){
        int item;
        for (int k = 0; k < 100; k++){
            item = (int)(Math.random() * 100) + 1;
            channelOutput.write(item);
        }
        channelOutput.write(-1);
        System.out.printf("Producer %d finished\n", id);
    }
}
