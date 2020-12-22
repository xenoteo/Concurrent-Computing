package xenoteo.com.github.lab10.bufferInfiniteManyPC;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

/**
 * Consumer class. Infinitely reads integers from input channel.
 */
public class Consumer implements CSProcess {
    private final One2OneChannelInt request;
    private final One2OneChannelInt channel;
    private final int id;

    public Consumer (One2OneChannelInt request, One2OneChannelInt channel, int id) {
        this.request = request;
        this.channel = channel;
        this.id = id;
    }

    public void run () {
        int item;
        while (true) {
            request.out().write(0);
            item = channel.in().read();
            System.out.printf("Consumer %d consumed value %d\n", id, item);
        }
    }

    public One2OneChannelInt getRequest() {
        return request;
    }

    public One2OneChannelInt getChannel() {
        return channel;
    }
}
