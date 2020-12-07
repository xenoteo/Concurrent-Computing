package xenoteo.com.github.lab7.ao;

import xenoteo.com.github.lab7.ao.clients.Consumer;
import xenoteo.com.github.lab7.ao.clients.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        final int THREADS_BOUNDER = 50;

        int consumerNumber = (int) (Math.random() * THREADS_BOUNDER) + 1;
        int producerNumber = (int) (Math.random() * THREADS_BOUNDER) + 1;

        int size = 10;
        Buffer buffer = new Buffer(size);

        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        int halfSize = size / 2;
        Proxy proxy = new Proxy(buffer);
        int simulationTime = 5;
        long finishTime = System.currentTimeMillis() + simulationTime * 1000;

        for(int i = 0; i < producerNumber; i++) producers.add(new Producer(i + 1, halfSize, proxy, finishTime));
        for(int i = 0; i < consumerNumber; i++) consumers.add(new Consumer(i + 1, halfSize, proxy, finishTime));

        ExecutorService executor = Executors.newCachedThreadPool();
        consumers.forEach(executor::execute);
        producers.forEach(executor::execute);

        ExecutorService scheduler = Executors.newCachedThreadPool();
        scheduler.execute(() -> {
            while (true){
                proxy.getScheduler().dispatch();
            }
        });


        try {
            executor.shutdown();
            scheduler.shutdown();
            executor.awaitTermination(simulationTime, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            executor.shutdownNow();
            scheduler.shutdownNow();
        }

        int operationsNumber = countOperations(producers, consumers);
        int sinNumber = countSinuses(producers, consumers);
        System.out.printf("In %d s executed %d client's operations and counted %d sinuses\n",
                simulationTime, operationsNumber, sinNumber);
    }

    private static int countOperations(List<Producer> producers, List<Consumer> consumers){
        int count = 0;
        for (Producer producer : producers)
            count += producer.getCount();
        for (Consumer consumer : consumers)
            count += consumer.getCount();
        return count;
    }

    private static int countSinuses(List<Producer> producers, List<Consumer> consumers){
        int count = 0;
        for (Producer producer : producers)
            count += producer.getSinCount();
        for (Consumer consumer : consumers)
            count += consumer.getSinCount();
        return count;
    }
}
