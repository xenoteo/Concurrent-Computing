package xenoteo.com.github.lab2.semaphores;

/**
 * Simple implementation of a counting semaphore.
 */
public class CountingSemaphore {
    /**
     * Number of free threads
     */
    private int value;

    public CountingSemaphore(int n){
        value = n;
    }

    public synchronized void acquire() throws InterruptedException {
        while (value <= 0) wait();
        value--;
    }

    public synchronized void release() throws InterruptedException{
        value++;
        notify();
    }
}