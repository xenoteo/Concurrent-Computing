package xenoteo.com.github.lab1;

import java.util.ArrayList;
import java.util.List;

/**
 * Multi-threaded data access concurrently without data protection.
 * Malfunctions of concurrent programs are shown.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        int operationsNumber = 100;
        int threadsNumber = 100;

        Counter counter = new Counter();   // starting with 0
        List<Thread> threads = new ArrayList<>();
        IncrementingAndDecrementing counting = new IncrementingAndDecrementing(counter, operationsNumber);

        for (int i = 0; i < threadsNumber; i++){
            Thread thread = new Thread(counting);
            thread.start();
            threads.add(thread);
        }

        for (int i = 0; i < threadsNumber; i++){
            threads.get(i).join();              // waiting until all threads are finished
        }

        System.out.println(counter.getVal());   // should be 0, but other values are expected
    }
}