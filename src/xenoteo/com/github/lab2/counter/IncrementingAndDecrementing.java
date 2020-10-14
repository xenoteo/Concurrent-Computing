package xenoteo.com.github.lab2.counter;

import xenoteo.com.github.lab1.Counter;
import xenoteo.com.github.lab2.semaphores.Semaphore;

/**
 * Task incrementing and decrementing a counter n times,
 * where n is a number provided in the constructor.
 * Using semaphores protection.
 */
public class IncrementingAndDecrementing implements Runnable{
    private Counter counter;
    private int n;
    private Semaphore semaphore;

    public IncrementingAndDecrementing(Counter counter, int n, Semaphore semaphore) {
        this.counter = counter;
        this.n = n;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            try{
                semaphore.acquire();
                counter.increment();
                semaphore.release();
                semaphore.acquire();
                counter.decrement();
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
