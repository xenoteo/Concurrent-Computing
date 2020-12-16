package xenoteo.com.github.lab7_8_9.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstration of producer-consumer problem, where each thread additionally counts sinuses after each operation.
 * Program counts how many sinuses were counted and how many operations were done in a provided time.
 */
public class Main {
    public static void main(String[] args) {
        final int THREADS_BOUNDER = 50;

        int consumerNumber = (int) (Math.random() * THREADS_BOUNDER) + 1;
        int producerNumber = (int) (Math.random() * THREADS_BOUNDER) + 1;

        int size = 50;
        Factory factory = new Factory(size);

        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        int halfSize = size / 2;
        int sinCount = 100;
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
            System.err.println("Tasks interrupted");
        }
        finally {
            executor.shutdownNow();
        }

        int operationsNumber = countOperations(producers, consumers);
        int sinNumber = countSinuses(producers, consumers);
        System.out.printf((char)27 + "[32m" + "\n%d producers and %d consumers in %d s executed %d client's operations and counted %d sinuses\n",
                producerNumber, consumerNumber, simulationTime, operationsNumber, sinNumber);
        System.out.println((char)27 + "[0m");
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