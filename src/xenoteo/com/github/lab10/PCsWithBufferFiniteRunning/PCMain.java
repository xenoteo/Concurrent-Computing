package xenoteo.com.github.lab10.PCsWithBufferFiniteRunning;

import org.jcsp.lang.*;

/**
 * Main program class for Producer/Consumer example.
 * Sets up channels, creates processes and then executes them in parallel, using JCSP.
 */
public final class PCMain {

    public static void main(String[] args) {
        final One2OneChannelInt[] producerChannels = {Channel.one2oneInt(), Channel.one2oneInt()};
        final One2OneChannelInt[] consumerRequests = {Channel.one2oneInt(), Channel.one2oneInt()};
        final One2OneChannelInt[] consumerChannels = {Channel.one2oneInt(), Channel.one2oneInt()};
        CSProcess[] procList = {
                new Producer(producerChannels[0], 0),
                new Producer(producerChannels[1], 100),
                new Buffer(producerChannels, consumerRequests, consumerChannels),
                new Consumer(consumerRequests[0], consumerChannels[0]),
                new Consumer(consumerRequests[1], consumerChannels[1])
        };
        Parallel parallel = new Parallel(procList);
        parallel.run();
    }

}