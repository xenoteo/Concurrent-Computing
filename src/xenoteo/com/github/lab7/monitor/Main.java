package xenoteo.com.github.lab7.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        final int THREADS_BOUNDER = 10;

        int consumerNumber = (int) (Math.random() * THREADS_BOUNDER) + 1;
        int producerNumber = (int) (Math.random() * THREADS_BOUNDER) + 1;

        int size = 10;
        Factory factory = new Factory(size);

        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        int halfSize = size / 2;
        int sinCount = 10;
        int simulationTime = 5;
        long finishTime = System.currentTimeMillis() + simulationTime * 1000;

        for(int i = 0; i < producerNumber; i++)
            producers.add(new Producer(i + 1, factory, halfSize, sinCount, finishTime));
        for(int i = 0; i < consumerNumber; i++)
            consumers.add(new Consumer(i + 1, factory, halfSize, sinCount, finishTime));

        ExecutorService executor = Executors.newCachedThreadPool();
        consumers.forEach(executor::execute);
        producers.forEach(executor::execute);

        try {
            executor.shutdown();
            executor.awaitTermination(simulationTime, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            executor.shutdownNow();
        }

        int operationsNumber = countOperations(producers, consumers);
        System.out.printf("Executed %d client's operations in %d s\n", operationsNumber, simulationTime);

    }

    private static int countOperations(List<Producer> producers, List<Consumer> consumers){
        int count = 0;
        for (Producer producer : producers)
            count += producer.getCount();
        for (Consumer consumer : consumers)
            count += consumer.getCount();
        return count;
    }
}