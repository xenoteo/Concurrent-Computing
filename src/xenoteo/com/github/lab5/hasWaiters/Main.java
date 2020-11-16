package xenoteo.com.github.lab5.hasWaiters;

import java.util.LinkedList;
import java.util.List;

/**
 * Demonstrating of producer-consumer problem.
 */
public class Main {
    public static void main(String[] args) {
        int producerNumber = 5;
        int consumerNumber = 10;
        int factorySize = 10;
        Factory factory = new Factory(factorySize);
        List<Thread> threads = new LinkedList<>();

        for (int i = 0; i < producerNumber; i++){
            Thread producer = new Thread(new Producer(i + 1, factory, factorySize / 2));
            threads.add(producer);
            producer.start();
        }

        for (int i = 0; i < consumerNumber; i++){
            Thread consumer = new Thread(new Consumer(i + 1, factory, factorySize / 2));
            threads.add(consumer);
            consumer.start();
        }

        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}