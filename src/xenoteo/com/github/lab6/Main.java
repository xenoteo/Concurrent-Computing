package xenoteo.com.github.lab6;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demonstration of producer-consumer problem using Lock and Condition interfaces.
 * Buffer moved to separate class, ticket system for producers and consumers.
 */
public class Main {

    public static void main(String[] args) {
        int consumerNumber = 1;
        int producerNumber = 2;

        int size = 10;

        Factory factory = new Factory(size);
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        for(int i = 0; i < producerNumber; i++) producers.add(new Producer(i + 1, factory));
        for(int i = 0; i < consumerNumber; i++) consumers.add(new Consumer(i + 1, factory));

        ExecutorService executor = Executors.newCachedThreadPool();

        consumers.forEach(executor::execute);
        producers.forEach(executor::execute);

        executor.shutdown();
    }
}
