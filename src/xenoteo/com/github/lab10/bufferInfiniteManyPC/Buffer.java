package xenoteo.com.github.lab10.bufferInfiniteManyPC;

import org.jcsp.lang.*;

import java.util.LinkedList;

/**
 * Buffer class. Manages communication between Producer and Consumer classes.
 */
public class Buffer implements CSProcess {
    private final Producer[] producers;
    private final Consumer[] consumers;
    private final LinkedList<Integer> buffer;
    private final int size;
    private Guard[] guards;

    public Buffer(Producer[] producers, Consumer[] consumers, int size) {
        this.producers = producers;
        this.consumers = consumers;
        this.size = size;
        buffer = new LinkedList<>();
        fillGuards();
    }

    /**
     * Fills the guard with producers input channels and consumers requests.
     */
    private void fillGuards(){
        guards = new Guard[producers.length + consumers.length];
        int i;
        for (i = 0; i < producers.length; i++){
            guards[i] = producers[i]
                    .getChannel()
                    .in();
        }
        for (; i < guards.length; i++){
            guards[i] = consumers[i - producers.length]
                    .getRequest()
                    .in();
        }
    }

    /**
     * Infinitely handles consumptions and productions.
     */
    public void run() {
        Alternative alt = new Alternative(guards);
        while (true) {
            int index = alt.select();
            if (index < producers.length){  // if producer
                if (buffer.size() < size) {
                    ChannelInputInt producersChannel = producers[index].getChannel().in();
                    int item = producersChannel.read();
                    buffer.add(item);
                }
            }
            else {                          // if consumer
                if (buffer.size() > 0) {
                    ChannelInputInt consumerRequest = consumers[index - producers.length].getRequest().in();
                    ChannelOutputInt consumerChannel = consumers[index - producers.length].getChannel().out();
                    consumerRequest.read();
                    int item = buffer.poll();
                    consumerChannel.write(item);
                }
            }
        }
    }
}