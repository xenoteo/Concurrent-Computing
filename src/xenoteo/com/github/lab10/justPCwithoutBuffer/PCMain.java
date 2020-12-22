package xenoteo.com.github.lab10.justPCwithoutBuffer;

import org.jcsp.lang.*;

/**
 * Main program class for Producer/Consumer example.
 * Sets up channel, creates one of each process and then executes them in parallel, using JCSP.
 */
public class PCMain {

    public static void main (String[] args) {
        final One2OneChannelInt channel = Channel.one2oneInt();
        CSProcess[] processes = { new Producer(channel), new Consumer(channel) };
        Parallel parallel = new Parallel(processes);
        parallel.run();
    }

}
