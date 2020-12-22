package xenoteo.com.github.lab10.bufferInfiniteManyPC;

import org.jcsp.lang.*;

/**
 * Main program class for Producer/Consumer example.
 * Sets up channels, creates processes and then executes them in parallel, using JCSP.
 */
public final class PCMain {

    public static void main(String[] args) {
        int producerNumber = 30;
        int consumerNumber = 30;
        int bufferSize = 10;

        CSProcess[] processes = new CSProcess[producerNumber + consumerNumber + 1];
        Producer[] producers = new Producer[producerNumber];
        Consumer[] consumers = new Consumer[consumerNumber];

        for (int i = 0; i < producerNumber; i++){
            Producer producer = new Producer(Channel.one2oneInt(), i + 1);
            producers[i] = producer;
            processes[i] = producer;
        }

        for (int i = 0; i < consumerNumber; i++){
            Consumer consumer = new Consumer(Channel.one2oneInt(), Channel.one2oneInt(), i + 1);
            processes[i + producerNumber] = consumer;
            consumers[i] = consumer;
        }

        Buffer buffer = new Buffer(producers, consumers, bufferSize);
        processes[processes.length - 1] = buffer;

        Parallel parallel = new Parallel(processes);
        parallel.run();
    }
}