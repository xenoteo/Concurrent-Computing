package xenoteo.com.github.lab7.monitor;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        final int THREADS_BOUNDER = 50;

        int consumerNumber = (int) (Math.random() * THREADS_BOUNDER) + 1;
        int producerNumber = (int) (Math.random() * THREADS_BOUNDER) + 1;

        int size = 10;
        Factory factory = new Factory(size);

        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        int halfSize = size / 2;

        for(int i = 0; i < producerNumber; i++) producers.add(new Producer(i + 1, factory, halfSize));
        for(int i = 0; i < consumerNumber; i++) consumers.add(new Consumer(i + 1, factory, halfSize));

        ExecutorService executor = Executors.newCachedThreadPool();
        consumers.forEach(executor::execute);
        producers.forEach(executor::execute);

        executor.shutdown();
    }
}