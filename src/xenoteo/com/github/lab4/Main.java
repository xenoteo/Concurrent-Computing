package xenoteo.com.github.lab4;

import java.util.LinkedList;
import java.util.List;

/**
 * Demonstrating of producer-consumer problem.
 */
public class Main {
    public static void main(String[] args) {
        int producerNumber = 1;
        int consumerNumber = 2;
        Factory factory = new Factory(1);
        List<Thread> threads = new LinkedList<>();

        for (int i = 0; i < producerNumber; i++){
            Thread producer = new Thread(new Producer(i + 1, factory));
            threads.add(producer);
            producer.start();
        }

        for (int i = 0; i < consumerNumber; i++){
            Thread consumer = new Thread(new Consumer(i + 1, factory));
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