package xenoteo.com.github.lab10.bufferInfiniteManyPC;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

/**
 * Producer class. Infinitely produce random integers in the range (0, 100].
 */
public class Producer implements CSProcess {
    private final One2OneChannelInt channel;
    private final int id;

    public Producer (One2OneChannelInt channel, int id) {
        this.channel = channel;
        this.id = id;
    }

    public void run (){
        while (true){
            int item = (int)(Math.random() * 100) + 1;
            channel.out().write(item);
            System.out.printf("Producer %d produced value %d\n", id, item);
        }
    }

    public One2OneChannelInt getChannel() {
        return channel;
    }
}
