package xenoteo.com.github.lab10.justPC;

import org.jcsp.lang.*;

/**
 * Consumer class. Reads 100 integers from input channel, displays them and then terminates.
 */
public class Consumer implements CSProcess {
    private final AltingChannelInputInt channelInput;

    public Consumer (final One2OneChannelInt channel) {
        channelInput = channel.in();
    }

    public void run () {
        for (int i = 0; i < 100; i++){
            int item = channelInput.read();
            System.out.printf("Consumed %d\n", item);
        }
    }
}
