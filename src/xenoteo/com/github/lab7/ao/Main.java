package xenoteo.com.github.lab7.ao;

import xenoteo.com.github.lab7.ao.clients.Consumer;
import xenoteo.com.github.lab7.ao.clients.Producer;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        final int MAX_THREADS_NUMBER = 50;

        int consumerNumber = (int) (Math.random() * MAX_THREADS_NUMBER) + 1;
        int producerNumber = (int) (Math.random() * MAX_THREADS_NUMBER) + 1;

        int size = 10;
        Buffer buffer = new Buffer(size);

        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        int halfSize = size / 2;
        Proxy proxy = new Proxy(buffer);

        for(int i = 0; i < producerNumber; i++) producers.add(new Producer(i + 1, halfSize, proxy));
        for(int i = 0; i < consumerNumber; i++) consumers.add(new Consumer(i + 1, halfSize, proxy));

        ExecutorService executor = Executors.newCachedThreadPool();
        consumers.forEach(executor::execute);
        producers.forEach(executor::execute);

        ExecutorService scheduler = Executors.newCachedThreadPool();

        scheduler.execute(() -> {
            while (true){
                proxy.getScheduler().dispatch();
            }
        });

        executor.shutdown();
        scheduler.shutdown();
    }
}
