package xenoteo.com.github.lab10.bufferFinitePC;

import org.jcsp.lang.*;

/**
 * Consumer class. Reads integers from input channel, displays them,
 * and then terminates when a negative value is read.
 */
public class Consumer implements CSProcess {
    private final ChannelOutputInt requestOutput;
    private final AltingChannelInputInt channelInput;

    public Consumer (final One2OneChannelInt req, final One2OneChannelInt in) {
        requestOutput = req.out();
        channelInput = in.in();
    }

    public void run () {
        int item;
        boolean running = true;
        while (running) {
            requestOutput.write(0);
            item = channelInput.read();
            if (item < 0)
                running = false;
            System.out.println(item);
        }
        System.out.println("Consumer finished");
    }
}
