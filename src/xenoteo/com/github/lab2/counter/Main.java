package xenoteo.com.github.lab2.counter;

import xenoteo.com.github.lab1.Counter;
import xenoteo.com.github.lab2.Semaphore;

import java.util.ArrayList;
import java.util.List;

/**
 * Multi-threaded data access concurrently with data protection.
 * Properly operation program is shown.
 */
public class Main {

    public static void main(String[] args) {
        int operationsNumber = 100;
        int threadsNumber = 100;

        Counter counter = new Counter();   // starting with 0
        List<Thread> threads = new ArrayList<>();
        IncrementingAndDecrementing counting =
                new IncrementingAndDecrementing(counter, operationsNumber, new Semaphore());

        for (int i = 0; i < threadsNumber; i++){
            Thread thread = new Thread(counting);
            thread.start();
            threads.add(thread);
        }

        try {
            for (int i = 0; i < threadsNumber; i++){
                threads.get(i).join();      // waiting until all threads are finished
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(counter.getVal());   // should be 0, but other values are expected
    }
}